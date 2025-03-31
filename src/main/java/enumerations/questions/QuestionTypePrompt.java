package enumerations.questions;

import exceptions.InvalidQuestionException;

public enum QuestionTypePrompt implements IQuestionType,I_DB_Filter {

    // IPv4
    //IP_IN_SAME_SUBNET(0, "SUB"),
    WHICH_PREFIX_IN_SAME_SUBNET(1, "PRE"),
    FIND_NET_ID(1, "NID"),
    FIND_IP_IN_SUBNET(1, "IIS"),
    FIND_BROADCAST(1, "BID"),
    FIND_NET_ID_AND_BROADCAST(2, "NAB"),
    FIND_MASK(1, "MSK"),
    FIND_OCTET(1, "OCT"),
    FIND_BINARY_OCTET(1, "OCT"),
    NUMBER_OF_DEVICES(1, "NOD"),

    // IPv6
    MAC_TO_IID(1, "M2I"),
    IID_TO_MAC(1, "I2M");

    public final int inputFieldAmount;
    public final String prefix;
    public final FilterTypes dbFilterType = FilterTypes.QuestionTypePrompt;

    QuestionTypePrompt(final int inputFieldAmount, final String prefix) {
        this.inputFieldAmount = inputFieldAmount;
        this.prefix = prefix;
    }

    @Override
    public IQuestionType getTypeFromID(final String id) {
        for(final QuestionTypePrompt questionType: QuestionTypePrompt.values()) {
            if(id.startsWith(questionType.prefix)) return questionType;
        }
        throw new InvalidQuestionException("No question with ID-prefix " + id.substring(0, 3));
    }

    @Override
    public FilterTypes getFilterType() {
        return dbFilterType;
    }


    public String getPrefix() {
        return prefix;
    }
}