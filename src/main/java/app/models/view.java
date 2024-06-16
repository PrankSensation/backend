package app.models;

public interface view {
    interface Id {}

    interface Sector extends Id {}

    interface User extends Sector {}

    interface Answer extends Id {}

    interface Category extends Id {}

    interface Question extends Answer, Category {}

    interface Questionnaire extends Question {}

    interface Result extends Id {}

    interface Attempt_info extends Id {}

    interface Attempt extends Attempt_info ,Questionnaire, Result {}
}
