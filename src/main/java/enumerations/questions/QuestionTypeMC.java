package enumerations.questions;
import static enumerations.questions.OSILayer.*;
import enumerations.questions.OSILayer;
import exceptions.InvalidQuestionException;
import java.util.Collections;
import java.util.List;

public enum QuestionTypeMC implements IQuestionType, I_DB_Filter {

    NAT("NAT", Collections.singletonList(L3_NETWORK)),
    DEFINITIONS("DEF", List.of(OSILayer.values())),
    SWITCHING("SWT", List.of(L2_DATA_LINK)),
    IPV4("IP4", List.of(L3_NETWORK)),
    SUBNETTING("SUB", List.of(L3_NETWORK)),
    IPV6("IP6", List.of(L3_NETWORK)),
    TCP("TCP", List.of(L4_TRANSPORT)),
    UDP("UDP", List.of(L4_TRANSPORT)),
    ;

    public final String prefix;
    public final List<OSILayer> layer;
    public final FilterTypes dbName = FilterTypes.QuestionTypeMC;

    QuestionTypeMC(final String prefix, List<OSILayer> layer) {
        this.prefix = prefix;
        this.layer = layer;
    }
    @Override
    public IQuestionType getTypeFromID(final String id) {
        for(final QuestionTypeMC questionType: QuestionTypeMC.values()) {
            if(id.startsWith(questionType.prefix)) return questionType;
        }
        throw new InvalidQuestionException("No question with ID-prefix " + id.substring(0, 3));
    }

    @Override
    public FilterTypes getFilterType() {
        return dbName;
    }

}
