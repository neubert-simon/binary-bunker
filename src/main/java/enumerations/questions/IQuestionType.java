package enumerations.questions;

public interface IQuestionType extends I_DB_Filter {

    IQuestionType getTypeFromID(final String id);

}