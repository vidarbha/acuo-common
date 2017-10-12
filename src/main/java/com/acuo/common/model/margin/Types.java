package com.acuo.common.model.margin;

import java.util.Arrays;

import static com.acuo.common.model.margin.Types.AssetType.Cash;
import static com.acuo.common.model.margin.Types.AssetType.NonCash;
import static com.acuo.common.model.margin.Types.ReasonCodeType.*;
import static com.acuo.common.model.margin.Types.ReasonCodeType.MarginCall;
import static com.acuo.common.model.margin.Types.ReasonCodeType.Pledge;

public interface Types {

    enum MarginType {
        Initial, Variation,InterestPayment,PAI,FeesAndComms,ProductCashFlow
    }

    enum CallType {
        Initial, Variation, Netted, Consolidated, Credit, Silo
    }

    enum NettingCallType {
        Initial, Variation, Credit, All
    }

    enum AssetType {
        Cash, NonCash
    }

    enum AssetSubType {

        CASH(Cash),
        TBILL(NonCash),
        NOTE(NonCash),
        BOND(NonCash),
        BILL(NonCash),
        EQUITY(NonCash);

        private AssetType parentType;

        AssetSubType(AssetType parentType){
            this.parentType = parentType;
        }

        public AssetType getParentType(){
            return parentType;
        }

        public static AssetSubType[] of(AssetType parentType) {
            return Arrays.stream(values())
                    .filter(assetSubType -> assetSubType.parentType == parentType)
                    .toArray(AssetSubType[]::new);
        }
    }

    enum BalanceStatus {
        Settled, Pending
    }

    enum ImRole {
        Secured, Pledgor
    }

    enum Role {
        Secured, Pledgor
    }

    enum AgreementType {
        Group, CSA, Regulatory_CSA, SCSA, MRA, CLEARED
    }

    enum DeliveryType {
        Deliver, Return
    }

    enum SecurityIdType {
        CASH, CUSIP, ISIN, RIC, SEDOL
    }

    enum BusinessState {
        Pending_New,
        Received,
        Agreed,
        Agree_Cancelled,
        Cancelled,
        Disputed,
        Dispute_Cancelled,
        Partial,
        Partial_Agreed,
        Partial_Disputed,
        Pledged,
        Pledge_Accepted,
        Pledge_Amended,
        Pledge_Cancelled,
        Pledge_Rejected,
        Rejected
    }

    enum AgreementBusinessState {
        Active,
        Cancelled,
        Change_Accepted,
        Change_Cancelled,
        Change_Rejected,
        Discontinuation_Accepted,
        Discontinuation_Cancelled,
        Discontinuation_Rejected,
        Discontinued,
        Pending_Assigned,
        Pending_Change,
        Pending_Discontinuation,
        Pending_New,
        Pending_Rejected
    }

    enum ExposureTreatment {
        Gross,
        Net
    }
    enum ReasonCodeType {
        Generic,
        Security,
        WebService,
        Database,
        XML,
        MarginCall,
        Pledge,
        RecallItem,
        InterestStatement,
        Other
    }

    enum ReasonCode {
        UNKNOWN_ERROR(1000, Generic, "Unknown Error"),
        INVALID_MARGIN_CALL_STATE(1002, Generic, "Invalid MarginCall State Transition"),
        INVALID_PELDGE_STATE(1003, Generic, "Invalid Pledge State Transition"),
        INVALID_RECALL_STATE(1004, Generic, "Invalid Recall State Transition"),
        DATE_PARSING_ERROR(1005, Generic, "Date Parsing Error"),
        MISSING_RECALL_ITEMS(1006, Generic, "Missing Recall Items"),
        MISSING_SUBSTITUTION_ITEMS(1006, Generic, "Missing Substitution Items"),
        CANNOT_ACT_ON_BO(1007, Generic, "Cannot act on business object"),
        INVALID_TRANSITION(1007, Generic, "Invalid Transition"),
        INVALID_SUBSTITUTION_STATE(1008, Generic, "Invalid Substitution State Transition"),
        INVALID_INTEREST_STATEMENT_STATE(1009, Generic, "Invalid Interest Statement State Transition"),
        OLD_VERSION_PASSED_IN(1010, Generic, "Old version passed in, please update and try again"),
        INVALID_AUTHORIZATION(2000, Security, "Invalid Authorization"),
        INVALID_PARMISSIONS(2001, Security, "Invalid Permissions"),
        PRECONDITION_FAILED(3000, WebService, "Precondition Failed"),
        BAD_REQUEST(3001, WebService, "Bad Request"),
        NOT_FOUND(3002, WebService, "Not Found"),
        BAD_GET_PARAMETER(3003, WebService, "Bad GET Parameter"),
        MAXIMUM_DETAIL_REQUEST_REACHED(3004, WebService, "Maximum number of detail requests exceeded"),
        MAXIMUM_INCOMING_REQUEST_REACHED(3005, WebService, "Maximum number of incoming request entities exceeded"),
        UNKNOW_DB_ERROR(4000, Database, "Unknown Database Error"),
        UNIQUE_CONSTRAINT_VIOLATION(4001, Database, "Unique Constraint Violation"),
        FOREIGN_KEY_CONSTRAINT_VIOLATION(4002, Database, "Foreign Key Constraint Violation"),
        UNKNOW_XML_ERROR(5000, XML, "Unknown XML Error"),
        JAXB_MARSHALLING_ERROR(5001, XML, "JAXB Marshalling Error"),
        JAXB_UNMARSHALLING_ERROR(5002, XML, "JAXB Unmarshaling Error"),
        UNKNOWN_BUSINESS_ERROR(9000, Other, "Unknown Business Error"),
        PORTFOLIO_DISCREPANCY(9001, MarginCall, "Portfolio Discrepancy"),
        INITIAL_MARGIN_DISCREPANCY(9002, MarginCall, "Initial Margin / Independent Amount Discrepancy"),
        COLLATERAL_DISCREPANCY(9003, MarginCall, "Collateral Discrepancy"),
        AGREEMENT_DISCREPANCY(9004, MarginCall, "Agreement Discrepancy"),
        NOTIFICATION_TIME(9005, MarginCall, "Notification Time"),
        CALL_AMOUNT_DISCREPANCY(9006, MarginCall, "Call Amount Discrepancy"),
        MTM_DISCREPANCY(9007, MarginCall, "MTM Discrepancy"),
        BELOW_THRESHOLD_LIMIT(9008, MarginCall, "Below Threshold Limit"),
        TWO_WAY_CALL(9009, MarginCall, "Two Way Call"),
        INELIGIBLE_COLLATERAL(9501, Pledge, "Ineligible Collateral"),
        INSUFFICIENT(9502, Pledge, "Insufficient Collateral"),
        INCORRECT_SETTLEMENT_PERIOD(9503, Pledge, "Incorrect Settlement Period"),
        INCORRECT_SSI_INSTRUCTIONS(9504, Pledge, "Incorrect SSI Instructions"),
        POSTED_COLLATERAL_INCORRECT(9505, Pledge, "Posted Collateral Incorrect"),
        DELIVERY_DATE(9601, RecallItem, "Delivery Date"),
        DONT_KNOW_COLLATERAL(9602, RecallItem, "Don't Know Collateral"),
        FX_RATE(9603, RecallItem, "Fx Rate"),
        PRICE(9604, RecallItem, "Price"),
        ITEM_OTHER(9605, RecallItem, "Other"),
        PAYMENT_AMOUNT_DISCREPANCY(9701, InterestStatement, "Payment Amount Discrepancy"),
        PARTY_RECEIVING_PAYMENT_AMOUNT_DISCREPANCY(9702, InterestStatement, "Party Receiving Payment Discrepancy"),
        OTHER(9999, Other, "Other"),
        UNDEFINED(1000000, Generic, "Undefined Error");

        private final int code;
        private final ReasonCodeType type;
        private final String description;

        ReasonCode(int code, ReasonCodeType type, String description) {
            this.code = code;
            this.type = type;
            this.description = description;
        }
    }

    enum DisputeReasonCode {
        PD, // 'Portfolio Discrepancy'
        ID, // 'Initial Margin/ Independent Amount Discrepancy'
        CD, // 'Collateral Discrepancy'
        AD, // 'Agreement Discrepancy'
        NT, // 'Notification Time'
        CAD, // 'Call Amount Discrepancy'
        MD, // 'MTM Discrepancy'
        BTL, // 'Below Threshold Limit'
        TWC, // 'Two Way Call'
        UBE, // 'UnKnown Business Error'
        OTH // 'Other'
    }
}
