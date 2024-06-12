package app.models;

import app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FillDatabase {

    @Autowired
    public QuestionnaireRepository questionnaireRepository;

    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    public SubCategoryRepository subCategoryRepository;

    @Autowired
    public StrengthOrWeaknessRepository strengthOrWeaknessRepository;

    @Autowired
    public SectorRepository sectorRepository;

    public void fill(){
        Questionnaire questionnaire = this.getQuestionnaire();
        this.questionnaireRepository.create(questionnaire);
    }

    private Questionnaire getQuestionnaire(){

        sectorRepository.create(new Sector("Landbouw, bosbouw en visserij"));
        sectorRepository.create(new Sector("Delfstoffenwinning"));
        sectorRepository.create(new Sector("ICT"));
        sectorRepository.create(new Sector("Maakindustrie"));

        Category Data = new Category("Data", "CSRD", "AI");

        SubCategory Contractbenutting = subCategoryRepository.create(new SubCategory("Contractbenutting"));
        Data.addSubCategory(Contractbenutting);

        SubCategory KPIs = subCategoryRepository.create(new SubCategory("KPI's"));
        Data.addSubCategory(KPIs);

        SubCategory Leveranciers = subCategoryRepository.create(new SubCategory("Leveranciers"));
        Data.addSubCategory(Leveranciers);

        SubCategory Materiaalwaarde = subCategoryRepository.create(new SubCategory("Materiaalwaarde"));
        Data.addSubCategory(Materiaalwaarde);

        SubCategory Supply_chain_transparantie = subCategoryRepository.create(new SubCategory("Supply chain transparantie"));
        Data.addSubCategory(Supply_chain_transparantie);

        Data.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 1 (ad hoc, old school)", "data agnostisch", 1)));

        Data.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 2 (eenmalig, geïsoleerd)", "data agnostisch", 2)));

        Data.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 3 (eenmalig, geïntegreerd)", "data agnostisch", 3)));

        Data.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 4 (gestructureerd, geïntegreed)", "data agnostisch", 4)));

        Data.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 5 (geïntegreerd, continu verbeterend)", "data agnostisch", 5)));

        categoryRepository.update(Data);


        Category Organisatie = new Category("Organisatie", "Belastingwetgeving (zowel expatregeling als belasting op arbeid vs materiaal >> ExTax)", "groei/krimp arbeidsbevolking, opleidingsniveau");

        SubCategory Competenties = subCategoryRepository.create(new SubCategory("Competenties"));
        Organisatie.addSubCategory(Competenties);

        SubCategory Functies_en_verantwoordelijkheden = subCategoryRepository.create(new SubCategory("Functies en verantwoordelijkheden"));
        Organisatie.addSubCategory(Functies_en_verantwoordelijkheden);

        SubCategory Positionering_inkoopfunctie_ = subCategoryRepository.create(new SubCategory("Positionering inkoopfunctie "));
        Organisatie.addSubCategory(Positionering_inkoopfunctie_);

        SubCategory Rol_opstelling_en_houding_inkoper = subCategoryRepository.create(new SubCategory("Rol, opstelling en houding inkoper"));
        Organisatie.addSubCategory(Rol_opstelling_en_houding_inkoper);

        SubCategory Tijd__geld = subCategoryRepository.create(new SubCategory("Tijd & geld"));
        Organisatie.addSubCategory(Tijd__geld);

        Organisatie.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 1 (ad hoc, old school)", "onaantrekkelijke werkgever", 1)));

        Organisatie.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 2 (eenmalig, geïsoleerd)", "onaantrekkelijke werkgever", 2)));

        Organisatie.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 3 (eenmalig, geïntegreerd)", "onaantrekkelijke werkgever", 3)));

        Organisatie.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 4 (gestructureerd, geïntegreed)", "onaantrekkelijke werkgever", 4)));

        Organisatie.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 5 (geïntegreerd, continu verbeterend)", "onaantrekkelijke werkgever", 5)));

        categoryRepository.update(Organisatie);


        Category Processen = new Category("Processen", "Sectorspecifieke eisen (?)", "fusies en overnames in de sector");

        SubCategory Behoeftestelling = subCategoryRepository.create(new SubCategory("Behoeftestelling"));
        Processen.addSubCategory(Behoeftestelling);

        SubCategory Contractmanagement = subCategoryRepository.create(new SubCategory("Contractmanagement"));
        Processen.addSubCategory(Contractmanagement);

        SubCategory Leveranciersmanagement = subCategoryRepository.create(new SubCategory("Leveranciersmanagement"));
        Processen.addSubCategory(Leveranciersmanagement);

        SubCategory Selectie = subCategoryRepository.create(new SubCategory("Selectie"));
        Processen.addSubCategory(Selectie);

        SubCategory Specificatie = subCategoryRepository.create(new SubCategory("Specificatie"));
        Processen.addSubCategory(Specificatie);

        Processen.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 1 (ad hoc, old school)", "inefficiënte of afwezige processen", 1)));

        Processen.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 2 (eenmalig, geïsoleerd)", "inefficiënte of afwezige processen", 2)));

        Processen.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 3 (eenmalig, geïntegreerd)", "inefficiënte of afwezige processen", 3)));

        Processen.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 4 (gestructureerd, geïntegreed)", "inefficiënte of afwezige processen", 4)));

        Processen.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 5 (geïntegreerd, continu verbeterend)", "inefficiënte of afwezige processen", 5)));

        categoryRepository.update(Processen);


        Category Strategisch_perspectief = new Category("Strategisch perspectief", "CSDDD", "Complexiteit van sector");

        SubCategory Besluitvorming = subCategoryRepository.create(new SubCategory("Besluitvorming"));
        Strategisch_perspectief.addSubCategory(Besluitvorming);

        SubCategory Duurzame_strategische_vraagstukken = subCategoryRepository.create(new SubCategory("Duurzame strategische vraagstukken"));
        Strategisch_perspectief.addSubCategory(Duurzame_strategische_vraagstukken);

        SubCategory Inkoopstrategie_visie_en_missie = subCategoryRepository.create(new SubCategory("Inkoopstrategie, visie en missie"));
        Strategisch_perspectief.addSubCategory(Inkoopstrategie_visie_en_missie);

        SubCategory Samenwerking = subCategoryRepository.create(new SubCategory("Samenwerking"));
        Strategisch_perspectief.addSubCategory(Samenwerking);

        SubCategory Toegevoegde_waarde_van_inkoop = subCategoryRepository.create(new SubCategory("Toegevoegde waarde van inkoop"));
        Strategisch_perspectief.addSubCategory(Toegevoegde_waarde_van_inkoop);

        Strategisch_perspectief.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 1 (ad hoc, old school)", "kan bestaan bij gratie voortbestaan oude wereld ", 1)));

        Strategisch_perspectief.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 2 (eenmalig, geïsoleerd)", "kan bestaan bij gratie voortbestaan oude wereld ", 2)));

        Strategisch_perspectief.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 3 (eenmalig, geïntegreerd)", "kan bestaan bij gratie voortbestaan oude wereld ", 3)));

        Strategisch_perspectief.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 4 (gestructureerd, geïntegreed)", "kan bestaan bij gratie voortbestaan oude wereld ", 4)));

        Strategisch_perspectief.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 5 (geïntegreerd, continu verbeterend)", "kan bestaan bij gratie voortbestaan oude wereld ", 5)));

        categoryRepository.update(Strategisch_perspectief);

        Category Toekomstbestendigheid = new Category("Toekomstbestendigheid", "Klimaatakkoord (55% minder CO2 in 2030, 95% in 2050)EU klimaatwet (klimaatneutraal in 2050)", "geopolitieke ontwikkelingen");

        SubCategory Adaptiviteit = subCategoryRepository.create(new SubCategory("Adaptiviteit"));
        Toekomstbestendigheid.addSubCategory(Adaptiviteit);

        SubCategory Digitalisering = subCategoryRepository.create(new SubCategory("Digitalisering"));
        Toekomstbestendigheid.addSubCategory(Digitalisering);

        SubCategory Omgaan_met_risico = subCategoryRepository.create(new SubCategory("Omgaan met risico"));
        Toekomstbestendigheid.addSubCategory(Omgaan_met_risico);

        SubCategory Stakeholdermanagement = subCategoryRepository.create(new SubCategory("Stakeholdermanagement"));
        Toekomstbestendigheid.addSubCategory(Stakeholdermanagement);

        SubCategory Trends__ontwikkelingen = subCategoryRepository.create(new SubCategory("Trends & ontwikkelingen"));
        Toekomstbestendigheid.addSubCategory(Trends__ontwikkelingen);

        Toekomstbestendigheid.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 1 (ad hoc, old school)", "paniekerige cultuur, loopt continu achter de feiten aan", 1)));

        Toekomstbestendigheid.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 2 (eenmalig, geïsoleerd)", "paniekerige cultuur, loopt continu achter de feiten aan", 2)));

        Toekomstbestendigheid.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 3 (eenmalig, geïntegreerd)", "paniekerige cultuur, loopt continu achter de feiten aan", 3)));

        Toekomstbestendigheid.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 4 (gestructureerd, geïntegreed)", "paniekerige cultuur, loopt continu achter de feiten aan", 4)));

        Toekomstbestendigheid.addStrengthOrWeakness(strengthOrWeaknessRepository.create(new StrengthOrWeakness("Niveau 5 (geïntegreerd, continu verbeterend)", "paniekerige cultuur, loopt continu achter de feiten aan", 5)));

        categoryRepository.update(Toekomstbestendigheid);

        Questionnaire questionnaire = new Questionnaire("Vragen lijst circulaire economie",
                """
                        Nederland wil in 2050 een volledig circulaire economie zijn en in 2030 al 50% minder primaire, abiotische grondstoffen gebruiken. Wij knokken ervoor om dat doel te halen. Wij zijn WIJ2030; we zetten ons in om de potentie van inkoop waar te maken en hiermee de transitie van lineair naar circulair te versnellen.

                        Dit doen we onder andere met het Inkoopontwikkelmodel. Dit model bestaat uit dit gratis zelfassessment en concrete tips voor verbetering. Hiermee krijgt iedereen die met inkoop te maken heeft binnen een organisatie op een eenvoudige manier perspectief en richting om verder te komen.

                        In de navolgende pagina's worden eerst een aantal algemene vragen gesteld over uw organisatie. Vervolgens volgen vijf categorieën vragen met elk vijf vragen over uw organisatie. Indien u geen antwoord heeft op een vraag óf de vraag is voor u niet relevant, kunt u deze vraag overslaan. Het invullen van de survey kost ongeveer 30 minuten en kan eventueel na onderbreking weer hervat worden.

                        Wij stellen het op prijs dat u de tijd neemt deze enquête in te vullen. Uw gegevens worden met zorg behandeld. Niets van de uitkomsten zal zonder uw expliciete toestemming worden gebruikt voor publicaties of acquisitiedoeleinden.

                        Hartelijk dank voor uw medewerking, namens alle ontwikkelaars,

                        Hogeschool van Amsterdam - Erica Goedhart
                        F-Fort - Nicole van Berkel en Jordie van Berkel-Schoonen"""
        );

        Question question11 = new Question("Is er sprake van eenduidig beleid en visie op strategische vraagstukken, als make-or-buy, verduurzaming en productontwikkeling (in de keten met strategische leverancier)?",4, Duurzame_strategische_vraagstukken);
        question11.addAnswer(new Answer("Het beleid dat de organisatie voor deze strategische vraagstukken heeft, is in ontwikkeling.", 1 , "Zet je in voor een centraal vastgelegd organisatiebeleid dat aandacht geeft aan strategische thema's.",1));
        question11.addAnswer(new Answer("Het beleid dat de organisatie voor deze strategische vraagstukken heeft, is centraal vastgesteld. Het beleid betreft 'klassieke' vraagstukken als 'make-or-buy' en productontwikkeling, maar over circulariteit wordt niets geschreven.",2,"Zet je in voor een organisatiebeleid waarin expliciet aandacht wordt gegeven aan circulariteit en zorg dat dit beleid centraal wordt vastgesteld.", 2));
        question11.addAnswer(new Answer("Het beleid dat de organisatie voor deze strategische vraagstukken heeft, is centraal vastgesteld. Het beleid heeft naast 'klassieke' doelstellingen ook duidelijke doelstellingen op het vlak van circulariteit geformuleerd", 3, "Zet je in voor het opstellen van jaarplannen die zijn afgeleid van het beleid, waarin de ambitie op circulariteit wordt vertaald naar concrete doelstellingen. Zorg daarbij voor afdelingsoverstijdende doelstellingen om suboptimalisatie te voorkomen.", 3));
        question11.addAnswer(new Answer("Als 3. Daarbij zijn er gedetailleerde jaarplannen over welke doelstellingen door de organisatie zullen worden behaald en hier wordt naar gehandeld.", 4, "Zet je in voor het monitoren, bespreken en bijsturen van de doelstellingen die jullie in de jaarplannen hebben opgesteld. Kijk daarbij expliciet over de grens van jouw eigen afdeling én van jouw directe leveranciers heen.", 4));
        question11.addAnswer(new Answer("Als 4. Deze plannen worden zorgvuldig gemonitord en er wordt bijgestuurd waar nodig. Van leveranciers wordt geëist dat zij de eisen op gebied van circulariteit ook naar hun leveranciers doorleggen.", 5, "Wow; jullie doen het onzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.", 5));
        questionnaire.addQuestion(question11);

        Question question12 = new Question(
                "Heeft de inkooporganisatie een strategisch plan dat aansluit bij de strategie van de organisatie?", 5,
                Inkoopstrategie_visie_en_missie
        );

        question12.addAnswer(new Answer(
                "Er zijn (nog) geen (strategische) inkoopplannen die afgeleid zijn van de strategie van de organisatie.",
                1,
                "Schrijf een missie, visie en strategie voor inkoop, die is afgeleid van de strategie van de organisatie.",
                1
        ));

        question12.addAnswer(new Answer(
                "De missie, visie en strategie van inkoop is vastgelegd in bijvoorbeeld een inkoopplan, maar dit is bij slechts enkelen bekend.",
                2,
                "Communiceer de inkoopstrategie richting stakeholders in de organisatie en zorg dat deze door hen ook gedragen wordt.",
                2
        ));

        question12.addAnswer(new Answer(
                "De missie en inkoopstrategie zijn geïntegreerd en worden organisatiebreed gedragen. Er zijn doelstellingen op het gebied van circulariteit opgenomen, waarbij het uitgangspunt vooral de huidige situatie is.",
                3,
                "Neem in de inkoopstrategie ook nadrukkelijk doelstellingen op die gericht zijn op het doorbreken van de status quo.",
                3
        ));

        question12.addAnswer(new Answer(
                "Als 3. Waarbij doelstellingen nadrukkelijk ook gericht zijn op de toekomst (doorbreken van bestaande patronen).",
                4,
                "Haal regelmatig op wat 'state of the art' is in jouw sector en pas de inkoopdoelstellingen daarop aan.",
                4
        ));

        question12.addAnswer(new Answer(
                "Als 4. De doelstellingen worden regelmatig getoetst op 'state of the art' en worden bijgesteld indien zij blijken niet te voldoen of indien de markt zich anders/sneller heeft ontwikkeld dan werd voorzien.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        Question question13 = new Question(
                "Welke toegevoegde waarde wordt inkoop door de directie toegekend?",6,
                Toegevoegde_waarde_van_inkoop
        );

        question13.addAnswer(new Answer(
                "De directie beschouwt inkoop als een uitvoerende activiteit.",
                1,
                "Ga in gesprek met jouw directie en leg hen uit welke invloed de inkoopfunctie heeft op het financieel resultaat.",
                1
        ));

        question13.addAnswer(new Answer(
                "De directie beseft dat de inkoopfunctie invloed heeft op het financieel resultaat. Directie is vooral geïnteresseerd in inkoopbesparingen, voorkomen van afhankelijkheid van leveranciers en vermijden van risico's.",
                2,
                "Voer een ander gesprek met jouw directie. Laat hen zien hoe inkoop óók kan bijdragen aan het verbeteren van de concurrentiepositie en aan het behalen van circulaire ambities.",
                2
        ));

        question13.addAnswer(new Answer(
                "De directie onderkent dat de inkoopfunctie van belang is voor concurrentiepositie (besparingen en innovaties) en voor het behalen van doelstellingen op het gebied van circulariteit. De focus ligt vooral op de huidige situatie en daarin voorkomende inkooptrajecten.",
                3,
                "Organiseer een inspiratiesessie met jouw directie en innovatieve leverancier of andere een koploper uit de sector. Zorg dat directie in mogelijkheden gaat denken.",
                3
        ));

        question13.addAnswer(new Answer(
                "Als 3. Daarbij wordt niet alleen gekeken naar het huidige inkoopportfolio, maar ook vraagstellingen aanwezig is gericht op de toekomst.",
                4,
                "Betrek jouw directie frequent bij strategische sessies met leveranciers en anderen uit de keten waarbij continu verbeteren op het gebied van circulariteit de doelstelling is.",
                4
        ));

        question13.addAnswer(new Answer(
                "Als 4. Hierbij wordt structureel kennis en inzicht vergaard bij leveranciers en andere partijen uit de keten. Deze inzichten worden gebruikt voor het aanscherpen/bijstellen van de eigen strategische beslissingen.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question13);

        questionnaire.addQuestion(question12);

        Question question14 = new Question(
                "Worden er multidisciplinaire teams samengesteld bij belangrijke inkooptrajecten? En kiest de organisatie bewust voor een rolverdeling?",
                6, Samenwerking
        );

        question14.addAnswer(new Answer(
                "De inkoopfunctie wordt gezien als primaire taak van de inkopers of inkoopafdeling.",
                1,
                "Breng de stakeholder in jouw organisatie in kaart. Zorg dat je weet wat hen drijft en waar zij op sturen.",
                1
        ));

        question14.addAnswer(new Answer(
                "Organisatie is zich  bewust dat samenwerking resultaten van inkooptrajecten verbetert. Op ad hoc basis worden multidisciplinaire teams samengesteld om belangrijke inkooptrajecten te doorlopen. Een rolverdeling wordt naar eigen inzicht iedere keer opnieuw gemaakt.",
                2,
                "Stel pro-actief, per inkoopcategorie een multidisciplinair team samen. Zorg dat deze teams bekend zijn binnen de organisatie en dat ze bij elkaar komen waar de situatie daar om vraagt.",
                2
        ));

        question14.addAnswer(new Answer(
                "Bij belangrijke inkooptrajecten worden zorgvuldig multidisciplinaire teams samengesteld met daarbij een doordachte rolverdeling. Er zijn enkele voorbeelden te noemen waarbij resultaten zijn behaald.",
                3,
                "Zet je in voor multi-disciplinaire doelstellingen voor het team op het gebied van circulariteit.",
                3
        ));

        question14.addAnswer(new Answer(
                "Als 3 Er zijn tevens resultaten behaald op gebied van circulariteit. Circulariteit wordt als gezamenlijke verantwoordelijkheid gezien, waarbij de focus ligt op (verbetering van) de huidige situatie.",
                4,
                "Zorg dat de multi-disciplinaire teams doelstellingen opstellen rondom circulariteit, waarin de doelstellingen elk jaar ambitieuzer zijn.",
                4
        ));

        question14.addAnswer(new Answer(
                "Als 4 waarbij de focus niet alleen ligt op de huidige situatie, maar nadrukkelijk ook op de gewenste toekomst, in lijn met de organisatiedoelstellingen.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question14);

        Question question15 = new Question(
                "Wordt de inkooporganisatie betrokken bij de interne besluitvorming van de organisatie?", 7,
                Besluitvorming
        );

        question15.addAnswer(new Answer(
                "Inkoopbeslissingen worden voornamelijk geleid door besluiten van de directie en managementteam.",
                1,
                "Stel een authorisatiematrix op, waarin operationele tekenbevoegdheid voor de inkoopafdeling en daarbuiten transparant geregeld zijn.",
                1
        ));

        question15.addAnswer(new Answer(
                "Inkoopbeslissingen worden genomen op basis van tekenbevoegdheid die de inkooporganisatie bepaalde authorisatie geeft om bestellingen te plaatsen die zijn afgedekt door raamcontracten die door directie/managementteam zijn getekend.",
                2,
                "Stel een authorisatiematrix op die een afspiegeling is van de tactische verantwoordelijkheid van inkoop.",
                2
        ));

        question15.addAnswer(new Answer(
                "Inkoopbeslissingen worden genomen op basis van tekenbevoegdheid die de inkooporganisatie bepaalde authorisatie geeft om redelijk autonoom beslissingen te nemen, ook voor nieuwe (raam)contracten. Dit betreft dan contracten/besluiten voor de huidige situatie en daarin voorkomende inkooptrajecten.",
                3,
                "Stel een authorisatiematrix op die een afspiegeling is van de rol die inkoop heeft bij het behalen van de circulaire ambities van de organisatie.",
                3
        ));

        question15.addAnswer(new Answer(
                "Als 3 echter de contracten/besluiten zijn nadrukkelijk ook gericht op de toekomst (doorbreken van bestaande patronen) en circulaire eisen.",
                4,
                "Stel een ambitiematrix op waarin je - naast operationele en tactische bevoegdheden - ook opneemt hoe je met afspraken omgaat die de bestaande patronen doorbreken. Het gaat hierin uitdrukkelijk ook over aangaan van nieuwe allianties.",
                4
        ));

        question15.addAnswer(new Answer(
                "Als 4. Daarbij wordt de inkooporganisatie gezien als aanjager van de circulaire transitie die de organisatie doormaakt. Er wordt (in samenwerking met andere afdelingen) veel aandacht besteed aan het sluiten van contracten met partijen die producten en diensten leveren die de circulaire transitie helpen versnellen.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question15);

        Question question21 = new Question(
                "Hoe is de inkoopfunctie gepositioneerd binnen de organisatie?", 8,
                Positionering_inkoopfunctie_
        );

        question21.addAnswer(new Answer(
                "Er is geen separate inkoopafdeling. Door de organisatie heen bestellen verschillende mensen. Niemand heeft het overzicht over wie wat koopt.",
                1,
                "Zet je in voor het vormen van een separate inkoopafdeling.",
                1
        ));

        question21.addAnswer(new Answer(
                "De inkoopafdeling / inkopers zijn lager geplaatst in de hiërarchie en vallen bijvoorbeeld onder financiën of facilitaire afdeling. Inkoop(manager) rapporteert aan managementlaag lager dan de directie.",
                2,
                "Zet je in voor een inkoopafdeling die op gelijkwaardig niveau acteert als andere ondersteunende afdelingen. Zorg dat inkoopmanager rapporteert aan directie of MT.",
                2
        ));

        question21.addAnswer(new Answer(
                "De inkoopafdeling en functie is minimaal gelijkwaardig aan facilitaire of andere ondersteunende afdelingen. Inkoopmanagement rapporteert aan directie en/of MT.",
                3,
                "Zorg dat inkoopmanagement mandaat en autonomie heeft. Zorg dat inkoop ook rapporteert over circulariteit.",
                3
        ));

        question21.addAnswer(new Answer(
                "Inkoopafdeling is minimaal gelijkwaardig aan de ondersteunende afdelingen in de hiërarchie en legt verantwoording af aan directie, ook op het gebied van circulariteit. Inkoopmanager heeft autonomie en mandaat.",
                4,
                "Onderneem acties die zorgen voor volwaardige erkenning door directie/MT van de inkoopfunctie, ook waar het gaat om circulariteit en concurrentiepositie van de organisatie.",
                4
        ));

        question21.addAnswer(new Answer(
                "Als 4 en er is sprake van volledige erkenning door directie en/of managementteam. De inkoopfunctie draagt bij aan circulariteit en het verbeteren van de concurrentiepositie van de organisatie.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question21);

        Question question22 = new Question(
                "Hoe is inkoop georganiseerd? Welke functies en verantwoordelijkheden zijn er binnen de inkoopafdeling vastgelegd?",
                9, Functies_en_verantwoordelijkheden
        );

        question22.addAnswer(new Answer(
                "Er zijn veel bestellers binnen de organisatie.",
                1,
                "Zet je in voor het kanaliseren van de inkooporders via de inkoopafdeling.",
                1
        ));

        question22.addAnswer(new Answer(
                "Er is een inkoopafdeling. Het werk wordt voornamelijk door operationele en soms tactische inkopers uitgevoerd. De aard van het werk is reactief.",
                2,
                "Creëer tactische en strategische inkoopfuncties en zorg dat je mensen met de juiste kennis en competenties hierop aanstelt.",
                2
        ));

        question22.addAnswer(new Answer(
                "Er is een inkoopafdeling met operationele, tactische en strategische inkopers. Er zijn geen echte specialismen; het werk wordt uitgevoerd door wie op dat moment tijd heeft.",
                3,
                "Verdeel het werk in inkoopcategorieën en zorg dat strategische en tactische inkopers zich specialiseren op de aan hun toegewezen inkoopcategorieën.",
                3
        ));

        question22.addAnswer(new Answer(
                "Er is een inkoopafdeling met operationele, tactische en strategische inkopers waarbij de strategische en tactische inkopers ingedeeld zijn per specialisme. Daardoor heeft men tijd zich in de eigen categorie te verdiepen in de 'state of the art' in de markt op gebied van circulariteit.",
                4,
                "Werk aan de promotie van je afdeling. Laat strategische/tactische inkopers gevraagd en ongevraagd advies geven over hun inkoopcategorieën en wat daarin bereikt kan worden op het vlak van circulariteit.",
                4
        ));

        question22.addAnswer(new Answer(
                "Als 3 waarbij het inkoopteam staat stevig op de kaart binnen de organisatie. Andere afdelingen zien de inkoopafdeling als een belangrijke bron van informatie voor de circulaire mogelijkheden in de markt.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question22);

        Question question23 = new Question(
                "Wat staat er in de competentieprofielen voor inkopers? Hoe wordt er gewerkt aan het vergroten van de professionaliteit van de inkooporganisatie?",
                10, Competenties
        );

        question23.addAnswer(new Answer(
                "Er zijn geen competentieprofielen voor inkopers. Er wordt intuïtief gezocht naar nieuwe medewerkers die passen bij de organisatie. Iedereen die in de smaak valt wordt aangenomen.",
                1,
                "Stel competentieprofielen op voor inkopers.",
                1
        ));

        question23.addAnswer(new Answer(
                "Er zijn competentieprofielen voor inkoop. Deze bevatten met name 'klassieke' en operationele competenties.",
                2,
                "Stel competentieprofielen op voor inkopers die nodig zijn voor handelen in een circulaire economie. Maak ook tijd en geld vrij voor voldoende (bij)scholing.",
                2
        ));

        question23.addAnswer(new Answer(
                "De functies beschikken over competentieprofielen en er zijn scholings-of ontwikkelplannen beschikbaar. Er is voldoende focus op competenties die van belang zijn voor circulair inkopen.",
                3,
                "Zet je in voor het vinden van medewerkers binnen inkoop die een ander gezichtspunt hebben dan wat al aanwezig is binnen de afdeling.",
                3
        ));

        question23.addAnswer(new Answer(
                "Als 3 waarbij er HR-plannen zijn om gericht deze (andere) mensen te recruiten. De eerste paar positieve resultaten zijn hiermee al behaald.",
                4,
                "Organiseer regelmatig sessies met de afdeling waarin mensen van elkaar leren hoe men circulariteit heeft invlochten in de eigen inkoopcategorie. Zorg voor een omgeving waarin mensen van elkaar kunnen en willen leren.",
                4
        ));

        question23.addAnswer(new Answer(
                "Als 4 waarbij er inmiddels een behoorlijke omslag is gemaakt en het merendeel van de inkopers beschikt in enige mate over competenties die van belang zijn voor circulair inkopen.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question23);

        Question question24 = new Question(
                "Hoe ziet de inkoper de eigen rol in de organisatie? Welke houding/opstelling neemt de inkoper aan?",
                11, Rol_opstelling_en_houding_inkoper
        );

        question24.addAnswer(new Answer(
                "De inkopers zien zichzelf als dienend aan budgethouders/opdrachtgevers. Als die geen eisen/wensen voor circulair inkopen opstellen, begint de inkoper er niet aan.",
                1,
                "Denk na over circulaire alternatieven per categorie en zorg dat de inkopers die alternatieven voorleggen aan budgethouders.",
                1
        ));

        question24.addAnswer(new Answer(
                "Inkopers hanteren de 'choose your battles' logica. Als het onderwerp niet veel problemen oplevert met opdrachtgevers/budgethouders wordt geprobeerd het circulaire alternatief te kopen.",
                2,
                "Train de inkopers in het aangaan van het gesprek met budgethouders over circulair inkopen. Zorg dat zij proberen budgethouders te overtuigen van mogelijkheden, ook al staan die er niet direct voor open.",
                2
        ));

        question24.addAnswer(new Answer(
                "Inkopers voelen zich gesteund door het beleid/ambitie van de organisatie en stellen bij veel inkopen die zij doen (zowel facilitair als primair) eisen op het gebied van circulariteit. In de uiteindelijke beslissing heeft de opdrachtgever/budgethouder de doorslaggevende stem en daar legt de inkoper zich bij neer.",
                3,
                "Organiseer commitment bij de budgethouders voor het circulair inkopen. Spreek met elkaar af welke circulaire KPI's worden nagestreefd en besluit op basis van die afspraken.",
                3
        ));

        question24.addAnswer(new Answer(
                "Als 3 er zijn tevens resultaten behaald op gebied van circulariteit. Circulair inkopen wordt als gezamenlijke verantwoordelijkheid gezien, waarbij de focus ligt op (verbetering van) de huidige situatie.",
                4,
                "Organiseer commitment bij de budgethouders voor het circulair inkopen. Spreek met elkaar af welke circulaire KPI's worden nagestreefd op lange termijn om concurrentiepositie van de organisatie te verstevigen of markten te veranderen.",
                4
        ));

        question24.addAnswer(new Answer(
                "Als 3 er zijn tevens resultaten behaald op gebied van circulariteit. De focus ligt niet alleen op de huidige situatie, maar nadrukkelijk ook op de gewenste toekomst.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question24);

        Question question25 = new Question(
                "Is er in uw organisatie genoeg tijd en budget beschikbaar voor medewerkers om kennis te vergaren op het gebied van duurzaam/circulair inkopen?",
                12, Tijd__geld
        );

        question25.addAnswer(new Answer(
                "Medewerkers hebben geen tijd om kennis te vergaren en competenties te ontwikkelen op het gebied van circulair inkopen. Er is geen budget beschikbaar voor ontwikkeling op dit vlak.",
                1,
                "Zet je in voor tijd en budget om kennis te vergaderen over circulair inkopen.",
                1
        ));

        question25.addAnswer(new Answer(
                "Het personeel heeft beperkt tijd om kennis te vergaren en competenties te ontwikkelen op het gebied van circulair inkopen. Er is zeer beperkt budget beschikbaar voor ontwikkeling op dit vlak.",
                2,
                "Borg dat inkopers regelmatig naar trainingen en bijeenkomsten gaan om zich te laten inspireren en kennis & vaardigheden rondom circulair inkopen op te doen.",
                2
        ));

        question25.addAnswer(new Answer(
                "Verscheidene personen binnen de inkooporganisatie hebben tijd om kennis te vergaren en competenties te ontwikkelen op het gebied van circulair inkopen. Voor de ontwikkeling/training is budget beschikbaar. Echter er is binnen de organisatie echter geen ruimte (tijd, lef en geduld) om nieuw opgedane kennis & ontwikkelde competenties in de dagelijkse praktijk structureel toe te passen.",
                3,
                "Evalueer regelmatig hoe opgedane kennis uit trainingen/bijeenkomsten worden toegepast in de dagelijkse praktijk. Maak concrete afspraken met elkaar hoe theorie zijn weg vindt naar jullie praktijk.",
                3
        ));

        question25.addAnswer(new Answer(
                "Als 3 waarbij er ruimte is om dit in de dagelijkse praktijk naar eigen inzicht toe te passen.",
                4,
                "Neem het initiatief om een ambassadeursprogramma op te zetten binnen de organisatie waarin mensen (van allerlei afdelingen) kennis delen en collega's inspireren over circulaire mogelijkheden en oplossingen. Empower dit ambassadeursprogramma vanuit de directie.",
                4
        ));

        question25.addAnswer(new Answer(
                "Als 3 waarbij de organisatie een (ambassadeurs)programma heeft om de opgedane kennis & ontwikkelde competenties structureel toe te passen en over te dragen op anderen binnen de organisatie (peer-to-peer learning). Hierdoor zet de kennis- & competentieontwikkeling zich verder voort in de eigen context/sector.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));


        questionnaire.addQuestion(question25);

        Question question31 = new Question(
                "In welke mate worden trends en ontwikkelingen gevolgd?",
                13, Trends__ontwikkelingen
        );

        question31.addAnswer(new Answer(
                "Inkoop leest de DEAL en houdt zo een beetje bij wat trends & ontwikkelingen in het vakgebied zijn.",
                1,
                "Praat met collega's over de ontwikkelingen in de markt en neem punten hieruit mee in de inkoopplannen.",
                1
        ));

        question31.addAnswer(new Answer(
                "Incidenteel wordt inkoopmarktonderzoek verricht, maar niet continue of systematisch.",
                2,
                "Ruim tijd in om jaarlijks marktonderzoek te doen voor de belangrijkste inkoopcategorieën.",
                2
        ));

        question31.addAnswer(new Answer(
                "Inkoopmarktonderzoek vindt structureel plaats op een aantal (hoofd)gebieden.",
                3,
                "Schrijf category sourcing plannen voor alle inkoopcategorieën en zorg voor actieve en passieve 'triggers' waardoor de externe analyses regelmatig worden ververst. Vanzelfsprekend is circulariteit een integraal onderdeel van deze plannen.",
                3
        ));

        question31.addAnswer(new Answer(
                "Inkoopmarktonderzoek vindt continu en op nagenoeg alle gebieden plaats. Er wordt gericht gezocht naar nieuwe ontwikkelingen op gebied van circulariteit waarbij de focus ligt op de onderhanden inkooptrajecten.",
                4,
                "Schrijf een plan voor de duurzame markttransformatie, waarin de bijdrage van de eigen organisatie in de transformatie van die markt wordt beschreven. In het plan komt aan de orde hoe middels inkoop een bijdrage zal worden geleverd aan die transformatie.",
                4
        ));

        question31.addAnswer(new Answer(
                "Als 4, waarbij de focus bij marktonderzoek ligt niet alleen op de huidige situatie, maar nadrukkelijk ook op de gewenste situatie waarbij wordt gezocht naar mogelijkheden 'hoger' op de R-ladder. Iemand uit de eigen (inkoop)organisatie is een autoriteit op gebied van circulair inkopen en wordt regelmatig gevraagd hierover anderen te inspireren.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question31);

        Question question32 = new Question(
                "Zijn risico's (kansen en bedreigingen) behorend bij de duurzame doelstellingen geïdentificeerd? En is vastgesteld hoe met deze risico's wordt omgegaan?",
                14, Omgaan_met_risico
        );

        question32.addAnswer(new Answer(
                "Er zijn geen risico's geïdentificeerd die horen bij circulaire doelen. Risicomanagement is vooral een financiële taak.",
                1,
                "Maak een algemene risicoinventarisatie voor de toeleveringen van de organisatie die aansluit op de circulaire doelen van de organisatie.",
                1
        ));

        question32.addAnswer(new Answer(
                "Er zijn risico's geïdentificeerd die aansluiten op de circulaire doelen van de organisatie. Deze risico's zijn echter op hoofdlijnen in kaart gebracht en worden niet regelmatig besproken.",
                2,
                "Vertaal de risicoinventarisatie naar inkoopcategorieën en formuleer beheersmaatregelen.",
                2
        ));

        question32.addAnswer(new Answer(
                "Er zijn risico's geïdentificeerd die aansluiten op de circulaire doelen van de organisatie en deze zijn doorvertaald naar inkooprisico's. Voor de grootste risico's zijn beheersmaatregelen geformuleerd.",
                3,
                "Introduceer risicogestuurd werken in de inkooporganisatie; zorg dat je zoekt naar diversiteit in oplossingen, neem eigenaarschap voor inkooprisico's en stimuleer gericht experimenteren.",
                3
        ));

        question32.addAnswer(new Answer(
                "Als 3. Echter de organisatie onderkent dat ze opereert in een snel veranderende wereld met 'wilde vraagstukken' (wicked problems). Er wordt gestreefd naar risicogestuurd werken, waarbij diversiteit in oplossingen wordt gezocht, eigenaarschap (voor nemen risico) is georganiseerd en gericht experimenteren (en daarmee toestaan van mogelijk verspilling) wordt gestimuleerd.",
                4,
                "Zet je in voor het benoemen van risicoleiders in jouw organisatie. Practice what you preach door daar bij inkoop mee te beginnen en door regelmatig risicoperceptie sessies met leveranciers te organiseren.",
                4
        ));

        question32.addAnswer(new Answer(
                "Als 4. Daarbij zijn er risicoleiders op alle niveaus in de organisatie. Risicosturing is formeel opgenomen in de rollen en taken van inkoop. Risicoperceptie en risicohouding worden zowel binnen de inkoopafdeling als met leveranciers regelmatig besproken.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question32);

        Question question33 = new Question(
                "Is er sprake van stakeholdermanagement waarbij rekening wordt gehouden met het krachtenveld van interne en externe belanghebbenden?",
                15, Stakeholdermanagement
        );

        question33.addAnswer(new Answer(
                "Nee, interne en externe stakeholders zijn niet in kaart gebracht.",
                1,
                "Breng interne en externe stakeholders van inkoop in kaart voor het inkoopplan.",
                1
        ));

        question33.addAnswer(new Answer(
                "Er is bewustwording en in het verleden is wel eens een stakeholder analyse uitgevoerd. Deze analyse is echter niet meer actueel.",
                2,
                "Borg dat de stakeholderanalyse minimaal eens per jaar wordt geüpdate.",
                2
        ));

        question33.addAnswer(new Answer(
                "Het maken van een stakeholderanalyse is (recentelijk) nog uitgevoerd in het kader van een inkooptraject. Er is aandacht hiervoor, maar is dit echter nog niet structueel. Er wordt ad hoc aandacht gegeven aan de interne belanghebbenden. De externe belanghebbenden zijn nog niet betrokken.",
                3,
                "Besteed tijd aan interne en externe stakeholders door hen te spreken als er sprake is van een inkooptraject of andere wijziging in de huidige situatie.",
                3
        ));

        question33.addAnswer(new Answer(
                "Het managen van de interne en externe stakeholders is vast onderdeel van een inkooptraject. Indien externe stakeholders deelnemen aan het vaststellen van de vraag, worden ze van meet af aan betrokken bij het onderzoeken van de opties (ISO20400), dit is echter nog geen continu proces.",
                4,
                "Benader interne en externe stakeholders pro-actief en regelmatig; ook buiten de noodzaak van een inkooptraject. Pas de stakeholder analyse waar nodig aan.",
                4
        ));

        question33.addAnswer(new Answer(
                "Als 4 waarbij het betrekken van externe stakeholders een continu proces is.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question33);

        Question question34 = new Question(
                "In welke mate is uw organisatie bezig met 'Procurement 4.0'?",
                16, Digitalisering
        );

        question34.addAnswer(new Answer(
                "Procurement 4.0?",
                1,
                "Verdiep je in wat Procurement 4.0 is.",
                1
        ));

        question34.addAnswer(new Answer(
                "De organisatie is daar niet mee bezig. Men duidt het als 'de volgende hype'.",
                2,
                "Schrijf in jouw inkoopbeleidsplan wat digitalisering zou kunnen betekenen voor jouw organisatie en processen.",
                2
        ));

        question34.addAnswer(new Answer(
                "Inkoop heeft zich ingelezen in de onderwerpen en heeft over digitalisering van processen in algemene zin wel wat in het inkoopbeleidsplan geschreven.",
                3,
                "Zet de punten uit het inkoopbeleidsplan om naar actie: experimenteer met een paar mogelijkheden.",
                3
        ));

        question34.addAnswer(new Answer(
                "Er zijn enkele pilots gedaan om operationele processen te automatiseren. De focus ligt op (verbetering van) de huidige situatie.",
                4,
                "Maak een roadmap met acties voor verdere digitalisering van processen én van de supply chain en voer die acties consequent uit.",
                4
        ));

        question34.addAnswer(new Answer(
                "Er is een visie op hoe digitalisering het inkoopvakgebied veranderen kan. In zowel operationele als in tactische processen worden state of the art digitaliseringsmogelijkheden ingezet om verduurzaming in de keten te bevorderen (AI, transparantie in keten, herkomst en toekomst van materialen).",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question34);

        Question question35 = new Question(
                "Is de organisatie in staat om zich tijdig aan te passen aan (steeds sneller) veranderende omstandigheden?",
                17, Adaptiviteit
        );

        question35.addAnswer(new Answer(
                "De organisatie richt zich op waar het zich altijd op heeft gericht. De noodzaak om circulair in te kopen wordt ontkend en de transitie naar een circulaire economie wordt gezien als een dure linkse lobby. De cultuur in de organisatie is behoudend.",
                1,
                "Zet je in voor het veranderen van de mindset binnen jouw organisatie. Kies je eigen woorden om uit te leggen wat de noodzaak is van een circulaire economie.",
                1
        ));

        question35.addAnswer(new Answer(
                "De organisatie erkent de noodzaak om meer circulair in te kopen maar ziet nog niet hoe ze hier 'geld mee kunnen verdienen'. Er zijn een paar minimale acties genomen om goederen/diensten circulair in te kopen, omdat dit wettelijk verplicht was of om daarmee voor de buitenwacht aan te geven dat er stappen worden ondernomen.",
                2,
                "Stel business cases op voor kansrijke inkoopcategorieën waarmee anderen ook al successen hebben geboekt. Laat in de business case zien hoe circulair inkopen monetaire én andere waarden voor jouw organisatie kan vergroten. Ga hiermee actief naar stakeholders in jouw organisatie.",
                2
        ));

        question35.addAnswer(new Answer(
                "De organisatie ziet circulair inkopen als onvermijdelijk om materiaalkosten en grondstoffenschaarste de baas te blijven. Organisatie zet vanuit die drijfveren stappen op het gebied van circulair inkopen. Hierbij volgt men stappen die door anderen al succesvol zijn genomen of die door de brancheorganisatie (of een ander collectief) gesteund worden. Processen worden ad hoc aangepast, hetgeen af en toe flink wat gedoe oplevert.",
                3,
                "Stel business cases op voor alle inkoopcategorieën en kies hierin ook nog niet ontgonnen gebied waarmee veel te winnen is voor jullie organisatie. Train en coach de inkopers in het brengen van het verhaal en het aanwakkeren van ondernemerschap bij collega's.",
                3
        ));

        question35.addAnswer(new Answer(
                "De organisatie ziet de transitie naar een circulaire economie als een kans voor (circulaire) innovaties en wil daarin voorop lopen. De processen zijn zo ingericht dat veranderingen in de markt of regelgeving snel kunnen worden opgepakt en inkoop speelt een belangrijke rol in het binnenfilteren van die veranderingen. De cultuur in de organisatie is ondernemend.",
                4,
                "Neem het initiatief om inkoopprocessen en andere processen zo in te richten dat ze verandering triggeren en op meer waarde sturen dan alleen financiële waarde. Onderken de kennis en kracht van de supply chain en geef leveranciers dus ook een actieve rol in het aanbrengen van vernieuwing.",
                4
        ));

        question35.addAnswer(new Answer(
                "De organisatie is overtuigd van de noodzaak van verschillende maatschappelijke transities en ziet daarin voor zichzelf een rol als aanjager. Niet alleen om kosten te besparen of innovaties tot stand te brengen, maar vanuit 'stewardship' voor de aarde en volgende generaties. De eigen processen zijn zo ingericht dat ze verandering triggeren en inkoop benut haar netwerk van leveranciers en branchegenoten maximaal om hen uit te dagen ook te veranderen. De cultuur in de organisatie is ondernemend en progressief.",
                5,
                "Wow; jullie doen het ontzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question35);

        Question question41 = new Question(
                "In welke mate zijn er KPI's geïmplementeerd die sturen op circulariteit?",
                18, KPIs
        );
        question41.addAnswer(new Answer(
                "Er zijn geen KPI's waarmee circulariteit wordt gemeten. Dit wordt ook niet als nodig gezien. Men is overtuigd dat sturen op kwaliteit en selectie van de juiste leveranciers voldoende is om ook duurzame/circulaire doelen te halen.",
                1,
                "Stel algemene voorwaarden op waarin duurzame bedrijfsvoering van leveranciers wordt geëist.",
                1
        ));
        question41.addAnswer(new Answer(
                "Er zijn algemene voorwaarden waarin duurzame bedrijfsvoering van de leveranciers wordt geëist. In praktijk wordt nalevering hiervan niet gecontroleerd.",
                2,
                "Bepaal KPI's voor de circulaire prestaties voor facilitaire producten/diensten en spreek die af met leveranciers.",
                2
        ));
        question41.addAnswer(new Answer(
                "Van niet-primaire (facilitaire) producten/diensten die worden gekocht worden één of meer duurzame/circulaire KPI's gemeten. De KPI's zijn wel redelijk algemeen en leveren niet echt goede stuurinformatie op.",
                3,
                "Bepaal KPI's voor de circulaire prestaties voor alle producten/diensten. Spreek die af met leveranciers en bespreek de prestatie regelmatig met leveranciers.",
                3
        ));
        question41.addAnswer(new Answer(
                "Als 3. Daarnaast zijn er voor de primaire goederen/diensten ook duurzame/circulaire KPI's opgesteld. Door de lessen die zijn geleerd met de facilitaire inkoop, kan men steeds beter de dialoog aan kunnen met de leveranciers.",
                4,
                "Creëer een dashboard met alle KPI's, waaronder zeker ook de circulaire prestaties. Deze KPI's worden - waar mogelijk geautomatiseerd - bijgehouden. In het dashboard wordt ook de opvolging van afwijkingen geregistreerd.",
                4
        ));
        question41.addAnswer(new Answer(
                "Als 4. Deze prestaties worden consequent gemeten en met deze informatie wordt (bij)gestuurd.",
                5,
                "Wow; jullie doen het onzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));
        questionnaire.addQuestion(question41);

        questionnaire.addQuestion(question41);

        Question question42 = new Question(
                "In hoeverre wordt de prestaties van leveranciers structureel gemeten en wordt actie genomen op deze prestaties?",
                19, Leveranciers
        );

        question42.addAnswer(new Answer(
                "Het ontbreekt aan de juiste middelen en de tijd om alles na te bellen. Betrokkenen vellen een oordeel over de leverancier op basis van ervaring.",
                1,
                "Richt het ERP en/of processen zo in dat prijsverschillen en tijdige levering tijdig worden gesignaleerd. Bespreek die prestaties met de leveranciers.",
                1
        ));
        question42.addAnswer(new Answer(
                "In een (ERP) systeem worden prijsverschillen en tijdige levering gemeten. Eens per kwartaal wordt de output geanalyseerd en waar nodig besproken tussen leverancier en opdrachtgever.",
                2,
                "Creëer een dashboard met alle KPI's, waaronder zeker ook de circulaire prestaties. Deze KPI's worden - waar mogelijk geautomatiseerd - bijgehouden.",
                2
        ));
        question42.addAnswer(new Answer(
                "Elke maand wordt een rapport gedraaid met daarin de prestatie van leveranciers. Dit overzicht wordt structureel geanalyseerd en waar nodig worden er verbeteracties over afgesproken met de leveranciers.",
                3,
                "Regel een proces in waarin het KPI dashboard met vaste frequentie (bijv. in een stand up maandagochtend) wordt besproken. Spreek concrete verbeteracties af met leveranciers, noteer deze ook op het dashboard en volg de acties op. Neem leverancierprestaties én hun verbeterpotentieel mee in toekomstige aanbestedingen/tenders.",
                3
        ));
        question42.addAnswer(new Answer(
                "Als 3, daarbij wordt de prestatie qua verbeteracties meegenomen als input voor volgende tender/RFQ/aanbesteding.",
                4,
                "Stimuleer leveranciers om ook ongevraagd met verbetervoorstellen of kansen in de markt te komen. Beloon dergelijk initiatief.",
                4
        ));
        question42.addAnswer(new Answer(
                "Als 4, waarbij leveranciers ook ongevraagd met verbetervoorstellen komen om prestaties te verbeteren of kansen in de markt te implementeren.",
                5,
                "Wow; jullie doen het onzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question42);

        Question question43 = new Question(
                "In welke mate is de herkomst en de 'toekomst' van materialen die worden gekocht bekend? Hoe wordt dit bijgehouden?",
                20, Supply_chain_transparantie
        );

        question43.addAnswer(new Answer(
                "Er is geen zicht op waar de gekochte materialen vandaan komen, anders dan de NAW gegevens van de 1e-lijns leveranciers.",
                1,
                "Breng voor strategische producten en diensten in kaart wat de leveranciers van jullie eigen leveranciers zijn (2e lijns). Waar significant breng je dit ook voor de 3e-lijns leveranciers in kaart.",
                1
        ));
        question43.addAnswer(new Answer(
                "Van strategische producten/diensten is bekend wat de 2e- en soms 3e-lijns leveranciers zijn.",
                2,
                "Breng de supply chain voor alle bottleneck en strategische inkoopcategorieën in kaart; van grondstof tot en met één stap na de levering/gebruik door de eigen organisatie.",
                2
        ));
        question43.addAnswer(new Answer(
                "Er is inzicht in de hele upstream supply chain van de strategische en de bottleneck leveranciers. Het is bekend welke kansen en bedreigingen er in de keten zijn met betrekking tot circulair inkopen.",
                3,
                "Regel een proces in waardoor de eigen organisatie regelmatig met strategische en bottleneck leveranciers de kansen en bedreigingen in de supply chain bespreekt. Daarbij wordt zowel naar het korte termijn als de lange termijn gekeken.",
                3
        ));
        question43.addAnswer(new Answer(
                "Als 3 en leveranciers rapporteren daar ook over.",
                4,
                "Breng de up- en downstream supply chain voor alle bottleneck en strategische inkoopcategorieën in kaart. Bepaal de kansen en bedreigingen in de keten. Zet verbeterprogramma's met leveranciers, klanten en anderen in de markt op om minder en andere grondstoffen te gebruiken en producten/halffabricaten langer in gebruik te houden.",
                4
        ));
        question43.addAnswer(new Answer(
                "De upstream én downstream supply chain is inzichtelijk. De oorsprong van de producten/materialen is bekend evenals hun 'toekomst' (bestemming na uitlevering door onze organisatie of einde gebruiksduur). De informatie wordt gedeeld met leveranciers om hoogwaardig hergebruik van materialen maximaal mogelijk te maken.",
                5,
                "Wow; jullie doen het onzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question43);

        Question question44 = new Question(
                "Hoe wordt omgegaan met de waarde van primaire/product related materialen/halffabricaten/producten? En hoe met die van facilitaire/non-product related materialen/halffabricaten/producten?",
                21, Materiaalwaarde
        );

        question44.addAnswer(new Answer(
                "Circulaire waarde? Het enige dat telt is de huidige financiële waarde.",
                1,
                "Bepaal circulaire waarde op een kwalitatieve manier. Omschrijf wat het belang kan zijn en wat het begrip voor jouw organisatie betekent.",
                1
        ));
        question44.addAnswer(new Answer(
                "Er is geen inzicht in de circulaire waarde van de goederen/materialen die worden of zijn gekocht. Het belang hiervan wordt wel onderschreven, maar geldende visie is dat iemand anders (grotere bedrijven, de wetgever, ...) eerst aan zet is om 'iets' te doen.",
                2,
                "Stel vast hoe jullie circulaire waarde willen meten en monitoren voor facilitaire producten/diensten die jullie inkopen. Start met het meten van die waarden.",
                2
        ));
        question44.addAnswer(new Answer(
                "Voor het in kaart brengen van de circulaire waarde van de faciliaire/non-product related producten die in gebruik zijn (zoals het gebouw, zonnepanelen, facilitaire goederen) is een eerste opzet gemaakt op basis van wat algemeen gangbare informatie.\nEr is geen inzicht van de primaire/product related materialen, halffabrikaten of producten. De markt lijkt daar niet rijp voor te zijn.",
                3,
                "Stel vast hoe jullie circulaire waarde willen meten en monitoren voor alle producten/diensten die jullie inkopen. Start met het meten van die waarden.",
                3
        ));
        question44.addAnswer(new Answer(
                "Voor het in kaart brengen van de circulaire waarde van de facilitaire/non-product related producten/materialen die in gebruik zijn (zoals het gebouw, zonnepanelen, facilitaire goederen) is contact gezocht met andere organisaties en collectieven die deze goederen/diensten kopen evenals met leveranciers en afvalverwerkers. Er lijkt echt samenwerking in bepaalde productgroepen op gang te komen.\nVoor primaire/product related producten/materialen is men gestart met overleg met leveranciers en klanten.",
                4,
                "Bespreek de circulaire waarde van producten/diensten met leveranciers. Koppel de meting aan de circulaire ambities en rapporteer hierover regelmatig richting zowel directie/MT, collega's en leveranciers.",
                4
        ));
        question44.addAnswer(new Answer(
                "Voor zowel facilitaire als primaire goederen, is in kaart gebracht wat de circulaire waarde is. Dit is gedaan door per productgroep alle direct belanghebbenden (leveranciers, klanten, financials, afvalverwerkers, ..) te benaderen. Ook zijn gesprekken gevoerd met collega's en concurrenten om zo de benodigde ecosystemen te creëren waarin materialen kunnen gaan circuleren.",
                5,
                "Wow; jullie doen het onzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question44);

        Question question45 = new Question(
                "Hoe wordt benutting van contracten gemonitord?",
                22, Contractbenutting
        );

        question45.addAnswer(new Answer(
                "Als een contract eenmaal getekend is, wordt erop vertrouwd dat de organisatie er gebruik van maakt.",
                1,
                "Neem een contractmanagement systeem in gebruik en monitor de benutting van de afgesloten contracten (in €).",
                1
        ));
        question45.addAnswer(new Answer(
                "Benutting van het contract wordt gemeten. De inkopers/contractmanagers volgen per inkoopcategorie welke spend er wel/niet onder contract wordt ingekocht.",
                2,
                "Regel een proces in waarin de gemonitorde uitgaven (in €) binnen en buiten het contract zichtbaar worden. Zorg dat de verantwoordelijk inkoper de spend die buiten het contract om gaat toetst op de mate waarin deze uitgaven voldoen aan de contractvoorwaarden.",
                2
        ));
        question45.addAnswer(new Answer(
                "Benutting van het contract wordt gemeten. De inkopers/contractmanagers volgen per inkoopcategorie welke spend er wel/niet onder contract wordt ingekocht. Spend die buiten contract wordt gekocht, wordt door de inkoper getoetst op de voor die categorie geldende criteria, onder andere die voor circulariteit.",
                3,
                "Regel een proces in waarbij de contract compliance wordt besproken met de budgethouders/bestellers in de organisatie. Zorg dat inkopers/contractmanagers de organisatie wijzen op het belang van het benutten van het contracten voor het behalen van de gestelde doelen.",
                3
        ));
        question45.addAnswer(new Answer(
                "Als 3 er zijn resultaten behaald op gebied van circulariteit en innovatie. Circulariteit en innovatie worden als gezamenlijke verantwoordelijkheid gezien, waarbij de focus ligt op (verbetering van) de huidige situatie.",
                4,
                "Zoek commitment bij budgethouders/bestellers in de organisatie, zodat het benutten van de contracten als een gezamenlijk doel wordt gevoeld dat noodzakelijk is om de lange termijn ambitie te behalen.",
                4
        ));
        question45.addAnswer(new Answer(
                "Als 3 er zijn resultaten behaald zowel op gebied van circulariteit en innovatie. Circulariteit en innovatie worden als gezamenlijke verantwoordelijkheid gezien, waarbij de focus niet alleen op de huidige situatie, maar nadrukkelijk ook op de gewenste toekomst ligt, in lijn met de organisatiedoelstellingen.",
                5,
                "Wow; jullie doen het onzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question45);

        Question question51 = new Question(
                "Bestaat er binnen de organisatie consensus over de aanpak van productgroepen/categorieën?",
                23, Behoeftestelling
        );

        question51.addAnswer(new Answer(
                "Nee. De inkoopafdeling krijgt de specificaties laat in het proces te horen en moet dan haasten om de goederen/diensten binnen de levertijd geleverd te krijgen",
                1,
                "Zorg dat inkopers contact hebben met budgethouders/behoeftestellers en weten welke wensen en eisen die hebben.",
                1
        ));
        question51.addAnswer(new Answer(
                "Nauwelijks. Af en toe is er overleg tussen inkoop en de budgethouder/behoeftesteller. Maar over het algemeen beslist een van twee partijen. Vaak denken zij anders over hoe de aanpak zou moeten zijn",
                2,
                "Organiseer dat inkopers geregeld spreken met budgethouders/behoeftestellers over de inkoopcategorieën waarvoor ze verantwoordelijk zijn. Zorg dat er een uitwisseling van ideeën plaatsvindt.",
                2
        ));
        question51.addAnswer(new Answer(
                "Soms. Doorgaans zijn inkoop en behoeftesteller het wel eens over de aanpak van facilitaire /non-product related goederen/diensten. Vooral in deze product categorieën wordt eenduidig richting leveranciers opgetreden.",
                3,
                "Organiseer dat inkopers regelmatig spreken met budgethouders/behoeftestellers over de inkoopcategorieën waarvoor ze verantwoordelijk zijn. Train inkopers in het voeren van een goede dialoog en het stellen van 'andere' vragen; vragen die circulair denken stimuleren.",
                3
        ));
        question51.addAnswer(new Answer(
                "Regelmatig. Voor veel product categorieën (primair en facilitair) zijn categorie strategieën opgesteld en daar wordt ook naar gehandeld. In de strategieën is een goede balans gevonden tussen eisen aan kwaliteit, prijs en circulariteit.",
                4,
                "Laat circulair denken en doen terugkomen in alle aspecten van het (inkoop)werk. Stimuleer elkaar zodat circulariteit het nieuwe normaal wordt en blijft.",
                4
        ));
        question51.addAnswer(new Answer(
                "Altijd. Door de interne governance én de cultuur is het onbestaanbaar dat behoeftestelling tot stand komt zonder dat alle relevante disciplines betrokken zijn. De strategieën zijn in lijn met en afgestemd op de organisatiedoelstellingen. In de strategieën is een goede balans gevonden tussen eisen aan kwaliteit, prijs en circulariteit.",
                5,
                "Wow; jullie doen het onzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question51);

        Question question52 = new Question(
                "In welke mate wordt in de specificaties van producten/diensten circulariteitseisen meegenomen?",
                24, Specificatie
        );

        question52.addAnswer(new Answer(
                "Niet. Specificaties zijn technisch, vaak toegeschreven naar een bepaald, bestaand en niet circulair product van een leverancier.",
                1,
                "Haal bij ervaringsdeskundigen (binnen en buiten de sector) op welke eisen en criteria men gebruikt heeft om facilitaire producten/diensten circulair uit te vragen.",
                1
        ));
        question52.addAnswer(new Answer(
                "Incidenteel. Er worden incidenteel wel facilitaire producten/diensten gekocht waarvoor circulaire specificaties gelden.",
                2,
                "Organiseer marktconsultaties waarin je op zoek gaat naar wat leveranciers kunnen. Leidt hiervan je eisen en criteria af voor de productspecificatie.",
                2
        ));
        question52.addAnswer(new Answer(
                "Soms en steeds vaker worden circulaire specificaties opgesteld of worden circulaire eisen gesteld aan de producten/diensten die worden gekocht; ook voor primaire producten. Daarbij wordt veelal op de kennis van leveranciers gesteund voor wat betreft de mogelijkheden.",
                3,
                "Voeg de informatie van de marktconsultaties samen met gedegen intern onderzoek, waarbij anders kijken naar producten en diensten wordt gestimuleerd (gericht op gebruik van minder materiaal, langere gebruiksduur van materiaal en betere herbestemming van materiaal).",
                3
        ));
        question52.addAnswer(new Answer(
                "Vaak. De R-ladder is wel bekend bij inkopers en engineers/behoeftestellers en die wordt gebruikt bij het opstellen van specificaties of eisen. Als een leveranciers een voorstel doet, wordt in gesprek gegaan over de mate van circulariteit van het product. Ook worden eisen gesteld aan de inrichting van processen waardoor het behouden van waarde van materialen wordt geborgd (preventief onderhoud, de herbruikbaarheid van de materialen aan het einde van de levensduur, etc.). Middels goed marktonderzoek weet men wat er op dit vlak mogelijk is.",
                4,
                "Organiseer out of the box-sessies, waarbij interne en externe partijen meedenken over de mogelijkheden voor een zo hoog mogelijk niveau op de R-ladder.",
                4
        ));
        question52.addAnswer(new Answer(
                "Altijd. De R-ladder is bij iedereen in de organisatie goed bekend. Specificaties die worden opgesteld, worden intern gechallenged op het streven naar een zo hoog mogelijk niveau op de R-ladder voordat ze worden uitgezet in de markt. Waar de organisatie afhankelijk is van specificaties door leveranciers (of hun fabrikanten), wordt bij de uitvraag meegegeven dat specificaties op circulariteit van product/dienst en proces worden getoetst.",
                5,
                "Wow; jullie doen het onzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question52);

        Question question53 = new Question(
                "Op basis van welke criteria worden leveranciers geselecteerd?",
                25, Selectie
        );

        question53.addAnswer(new Answer(
                "Prijs, levertijd en kwaliteit zijn de criteria, waarbij prijs het zwaarst weegt van allemaal. Verder zijn er algemene inkoopvoorwaarden waaraan de leverancier moet voldoen, maar in de praktijk wordt niet getoetst of daar ook echt aan wordt voldaan.",
                1,
                "Formuleer minimaal één gunningscriterium dat gaat over circulariteit.",
                1
        ));
        question53.addAnswer(new Answer(
                "Naast prijs, levertijd en kwaliteit, is circulariteit een criterium. Het wordt bij de leverancier gelaten hoe dit wordt ingevuld.",
                2,
                "Formuleer een evenwichtige set aan gunningscriteria waarbij prijs, levertijd, kwaliteit en circulariteit allemaal aan bod komen. Zorg dat circulariteit daarin helder gedefinieerd wordt.",
                2
        ));
        question53.addAnswer(new Answer(
                "Circulariteit vormt een even belangrijk selectiecriterium als prijs, kwaliteit en levertijd. Er is kennis van de markt, daardoor wordt circulariteit verder gedefinieerd en worden daarbinnen eisen gesteld.",
                3,
                "Stel gunningscriteria op voor circulariteit, kwaliteit en TCO. Zorg voor een heldere definitie van alle criteria en maak duidelijk aan leveranciers hoe je die gaat meten.",
                3
        ));
        question53.addAnswer(new Answer(
                "Circulariteit, kwaliteit en Total Cost of Ownership zijn de criteria die minimaal worden gehanteerd. In de eisen die eerder in het proces worden gesteld (long list >> short list) worden niet-duurzame partijen al uitgesloten. Daarmee wordt een eerlijke afweging voor de lange termijn gemaakt.",
                4,
                "Neem in je aanbesteding/tender op dat er een LCC moet worden aangeleverd voor de te leveren producten. Stel daarnaast gunningscriteria op voor het proces dat moet zorgen dat producten daadwerkelijk kunnen gaan circuleren (ook na levering/gebruik).",
                4
        ));
        question53.addAnswer(new Answer(
                "Bij elke aanschaf wordt een LCC (Life Cycle Cost cfm ISO 20400) berekening gemaakt. Op basis daarvan vindt de selectie plaats. Daarnaast vormen de mate waarin de leverancier kan aantonen dat hun proces circulair is ingericht en de juiste prikkels in hun circulaire verdienmodel belangrijke criteria bij de selectie/gunning.",
                5,
                "Wow; jullie doen het onzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question53);

        Question question54 = new Question(
                "Hoe worden selectiecriteria vertaald naar contracteisen? En in welke mate wordt in de uitvoering daar naar gehandeld? Hoe worden productinnovaties gedurende de looptijd van het contract meegenomen?",
                26, Contractmanagement
        );

        question54.addAnswer(new Answer(
                "De criteria die worden gehanteerd in de tender/aanbesteding, blijken vaak niet in contracten terecht te komen.",
                1,
                "Regel een proces in dat borgt dat in tender/aanbesteding gestelde eisen ook in het contract komen.",
                1
        ));
        question54.addAnswer(new Answer(
                "De in de tender/aanbesteding gehanteerde criteria komen in de contracten terecht. Alleen zijn deze niet allen goed meetbaar, waardoor tijdens de uitvoering blijkt dat er nogal eens verschil in interpretatie zit.",
                2,
                "Laat inkoper en contractmanager samen werken aan het opstellen van duidelijk meetbare prestatie indicatoren betreffende het contract.",
                2
        ));
        question54.addAnswer(new Answer(
                "De in de tender/aanbesteding gehanteerde criteria komen in de contracten terecht en zijn ook goed meetbaar. De leveranciersprestatie wordt in praktijk wel vooral op prijs en levertijd opgevolgd.",
                3,
                "Regel een proces in dat zorgt dat de inkoper/contractmanager vanaf het begin ook scherp de circulaire doelstellingen opvolgt.",
                3
        ));
        question54.addAnswer(new Answer(
                "Als 3 Er worden ook leveranciersprestaties op het gebied van circulariteit gemeten. Circulariteit wordt als gezamenlijke verantwoordelijkheid gezien, waarbij de focus ligt op (verbetering van) de huidige situatie.",
                4,
                "Stuur op de prestatie indicatoren van het contract. Zorg daarbij dat er op de ambitieuze, game-changer circulaire doelstellingen vanaf het begin actie wordt ondernomen.",
                4
        ));
        question54.addAnswer(new Answer(
                "Als 4 waarbij de focus niet alleen op de huidige situatie, maar nadrukkelijk ook op de gewenste toekomst ligt.",
                5,
                "Wow; jullie doen het onzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question54);

        Question question55 = new Question(
                "Welke strategie ligt ten grondslag aan de leveranciers waarmee wordt samengewerkt? En hoe ontwikkelt die samenwerking zich in de loop van de tijd?",
                27, Leveranciersmanagement
        );

        question55.addAnswer(new Answer(
                "Binnen de inkoopafdeling zijn geen specialismen; het werk wordt opgepakt door degene die op dat moment tijd heeft.",
                1,
                "Zorg dat strategische en tactische inkopers zich specialiseren op de aan hun toegewezen inkoopcategorieën. Zorg dat deze inkopers op de hoogte zijn van de prestaties van de leverancier in hun inkoopcategorie.",
                1
        ));
        question55.addAnswer(new Answer(
                "Binnen de inkoopafdeling zijn mensen naar specialismen ingedeeld. Een contract is altijd in beheer bij een de vaste contractmanager/inkoper van het betreffende specialisme (inkoopcategorie). Deze persoon houdt in de gaten of de leverancier levert conform verwachting. Als dit niet zo is, wordt de leverancier daarop aangesproken.",
                2,
                "Stel zeker dat strategische en tactische inkopers weten wat er speelt bij de leveranciers in hun categorie. Dit is meer dan hun prestaties; het gaat er ook om te snappen wat er in de markt van de leveranciers gaande is.",
                2
        ));
        question55.addAnswer(new Answer(
                "Elke productgroep heeft een category strategie en een vaste contractmanager/inkoop. In de category strategy is veel aandacht voor vergroten van de mate van circulariteit en de impact daarvan.",
                3,
                "Train de strategische en tactische inkopers op gebied van relatiemanagement. Leer hen alert te zijn op directe en indirecte signalen die van belang zijn in de samenwerking.",
                3
        ));
        question55.addAnswer(new Answer(
                "Als 3. De contractmanager/inkoper houdt in de gaten of er in de markt / bij de leveranciers iets verandert waardoor onze belangen zouden kunnen wijzigen. Als dat zo is, wordt daarop geacteerd.",
                4,
                "Zorg dat er op gestructureerde wijze aandacht wordt gegeven aan de 'chain of command'. Laat strategische en tactische inkopers initieren dat er ook op management en directie niveau gesprekken tussen eigen organisatie en die van de leverancier plaatsvinden.",
                4
        ));
        question55.addAnswer(new Answer(
                "Als 4. De leveranciers binnen een category kent men goed: het is bekend wat over en weer de belangen zijn en de 'chain of command' is helder in beeld. Er wordt zowel op operationeel en tactisch niveau contact onderhouden. Er wordt ook op strategisch niveau gesproken om samen met ketenpartners de sector de transitie naar een circulaire economie te versnellen.",
                5,
                "Wow; jullie doen het onzettend goed. Zorg dat je jouw successen deelt met anderen binnen en buiten jouw sector.",
                5
        ));

        questionnaire.addQuestion(question55);

        questionnaire.setIsActive(false);

        return questionnaire;
    }
}
