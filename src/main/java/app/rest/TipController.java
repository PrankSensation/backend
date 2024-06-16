package app.rest;

import app.models.*;
import app.repositories.AnswerRepository;
import app.repositories.CategoryRepository;
import app.repositories.TipTextRepository;
import app.repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tip")
public class TipController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TipTextRepository tipTextRepository;

    @Autowired
    private ResultController resultController;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("1/{attemptUuid}")
    public ResponseEntity<List<Tip>> getTipOne(@PathVariable String attemptUuid){
        /*
        Get the short term tips otherwise known as tip 1.
         */
        List<Tip> tipOneTips = answerRepository.getShortTermTips(attemptUuid);
        if (tipOneTips.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
//        Client only wants 2 shortterm tips.
        tipOneTips = tipOneTips.subList(0, 2);
        return ResponseEntity.ok(tipOneTips);
    }

    @GetMapping("2/personal/{attemptUuid}/{userUuid}")
    public ResponseEntity<Map<TipTextPart, String>> getTipTwo(@PathVariable String attemptUuid, @PathVariable String userUuid){
        /*
        Get middle long term tips otherwise known as tip2.
         */
        Map<String, Double> user_avg_by_category = resultController.getUserResultsByAttemptId(attemptUuid).getBody();
        Map<String, Double> sector_avg_by_category = resultController.getSectorResultsForLatestAttempts(attemptUuid).getBody();

        List<String> categories_sorted_by_avg_difference = this.sortByAvgDifference(user_avg_by_category, sector_avg_by_category);

        if (categories_sorted_by_avg_difference == null) {
            return ResponseEntity.notFound().build();
        }

        Sector users_sector = userRepository.getUserSector(userUuid);

        List<TipText> tipTexts = tipTextRepository.getTipTextForTipType(TipType.tip2);

        //Get the lowest scoring category compared to sector.
        String lowest_scoring_category = categories_sorted_by_avg_difference.get(categories_sorted_by_avg_difference.size() - 1);
//        Get the highest scoring category compared to sector.
        String highest_scoring_category = categories_sorted_by_avg_difference.get(0);

        Map<String, String> placeholder_mapping = Map.of(
                "categorie_hoger_dan_gemiddeld", highest_scoring_category,
                "categorie_lager_dan_gemiddeld", lowest_scoring_category,
                "link_sector_plan", "<a href='"+ users_sector.getLink() + "'>"+ users_sector.getLink() +"</a>",
                "best_in_class", users_sector.getBest_organisation(),
                "boegbeeld_onderzoek", users_sector.getResearcher(),
                "boegbeeld_praktijk", users_sector.getPerson_of_intrest(),
                "gebruiker_sector", users_sector.getTitle()
        );

//        Personalizing tip text.
        Map<TipTextPart, String> tipTextPartTextMapping = replace_placeholders_in_tip_texts(tipTexts, placeholder_mapping);

//        Handels edge cases where users score's lower higher or equal to sector.
        alter_text_part_one_when_needed(tipTextPartTextMapping, lowest_scoring_category, highest_scoring_category,
                user_avg_by_category, sector_avg_by_category);

        return ResponseEntity.ok(tipTextPartTextMapping);
    }

    @GetMapping("3/{attemptUuid}")
    public ResponseEntity<Map<TipTextPart, String>> getTipThree(@PathVariable String attemptUuid){
        Map<String, Double> user_avg_by_category = resultController.getUserResultsByAttemptId(attemptUuid).getBody();
        if (user_avg_by_category == null || user_avg_by_category.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

//        Gets the lowest category of this user.
        String lowest_category_title = user_avg_by_category.keySet().stream().
                sorted(Comparator.comparing(user_avg_by_category::get)).toList().get(0);

//        Gets score of the lowest category of this user.
        int lowest_category_score = (int) Math.round(user_avg_by_category.get(lowest_category_title));

//        Gets category object of the lowest category this user.
        Category lowest_category = categoryRepository.get_category_by_title(lowest_category_title);

        Map<String, String> placeholder_mapping = Map.of(
                "laagste_aspect", lowest_category_title,
                "situatie_statement", lowest_category.getStrengthOrWeaknesses()
                        .get(lowest_category_score - 1).getStatement(),
                "wetgeving_met_invloed", lowest_category.getThreat_law(),
                "markt_ontwikkeling_invloed", lowest_category.getThreat_market()
        );

        List<TipText> tipTexts = tipTextRepository.getTipTextForTipType(TipType.tip3);

//        Personalizing tip text.
        Map<TipTextPart, String> tipTextPartTextMapping = replace_placeholders_in_tip_texts(tipTexts, placeholder_mapping);

        return ResponseEntity.ok(tipTextPartTextMapping);
    }

    @GetMapping("/tipTwo")
    public ResponseEntity<List<TipText>> getTipTwo(){
        List<TipText> tipTexts = tipTextRepository.getTipTextForTipType(TipType.tip2);
        return ResponseEntity.ok(tipTexts);
    }
    @GetMapping("/tipThree")
    public ResponseEntity<List<TipText>> getTipThree(){
        List<TipText> tipTexts = tipTextRepository.getTipTextForTipType(TipType.tip3);
        return ResponseEntity.ok(tipTexts);
    }


    @PutMapping("/admin/tipText")
    public ResponseEntity<List<TipText>> updateMultipleTipText(@RequestBody List<TipText> tipTexts){
        for (TipText tipText : tipTexts) {
            tipTextRepository.updateTipText(tipText);
        }
        return ResponseEntity.ok(tipTexts);
    }

    private List<String> sortByAvgDifference(Map<String, Double> user_avg_by_category,
                                             Map<String, Double> sector_avg_by_category){
        /*
          Returns list of keys sorted by difference in value.
         */
        if (user_avg_by_category == null || sector_avg_by_category == null ||
                user_avg_by_category.isEmpty() || sector_avg_by_category.isEmpty()) {
            return null;
        } else {
            return user_avg_by_category.keySet().stream().sorted(Comparator.comparingDouble(
                    key -> user_avg_by_category.get(key) - sector_avg_by_category.get(key)).reversed()).toList();
        }
    }

    private Map<TipTextPart, String> replace_placeholders_in_tip_texts(List<TipText> tipTexts, Map<String, String> placeholder_mapping){
        return tipTexts.stream()
                .collect(Collectors.toMap(TipText::getTip_text_part, tipText ->
                        tipText.getTextWithReplacedPlaceholders(placeholder_mapping)));
    }

    private void alter_text_part_one_when_needed(Map<TipTextPart, String> tipTextPartTextMapping,
                                                 String lowest_scoring_category,
                                                 String highest_scoring_category,
                                                 Map<String, Double> user_avg_by_category,
                                                 Map<String, Double> sector_avg_by_category){
        /*
        Handels edge cases where scores are equal or user is better or worse on all categories.
         */
        if (user_avg_by_category.get(lowest_scoring_category) > sector_avg_by_category.get(lowest_scoring_category)) {
//            Text for when user scores higher than sector
            tipTextPartTextMapping.put(TipTextPart.TipTextPart1, "Vergeleken met anderen uit jullie sector scoren jullie boven gemiddeld op alle aspecten, goed gedaan!");
        } else if (areMapsEqual(user_avg_by_category, sector_avg_by_category)) {
//            Text for when user scores the same as the sector
            tipTextPartTextMapping.put(TipTextPart.TipTextPart1, "Jullie scoren op alle aspecten hetzelfde als gemiddeld in jullie sector.");
        } else if (user_avg_by_category.get(highest_scoring_category) < sector_avg_by_category.get(lowest_scoring_category)) {
//            Text for when user scores lower as than sector
            tipTextPartTextMapping.put(TipTextPart.TipTextPart1, "Vergeleken met anderen uit jullie sector scoren jullie onder gemiddeld op alle aspecten.");
        }
    }

    private static boolean areMapsEqual(Map<String, Double> userAvgByCategory, Map<String, Double> sectorAvgByCategory) {
        // Check if the sizes of the maps are the same
        if (userAvgByCategory.size() != sectorAvgByCategory.size()) {
            return false;
        }

        // Check if all entries in userAvgByCategory are present in sectorAvgByCategory with the same values
        for (Map.Entry<String, Double> entry : userAvgByCategory.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();

            // Check if sectorAvgByCategory contains the key
            if (!sectorAvgByCategory.containsKey(key)) {
                return false;
            }

            // Check if the values are the same
            if (!value.equals(sectorAvgByCategory.get(key))) {
                return false;
            }
        }

        // If all entries match, the maps are equal
        return true;
    }




}
