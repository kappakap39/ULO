package com.kbank.eappu.enums;

public final class MessageEnum {
	public static final String escLessThan = "&lt;";
	public static final String escMoreThan = "&gt;";
	public static final String ucLessThan = "\\u003c";
	public static final String ucMoreThan = "\\u003e";
	public static final String oriLessThan = "<";
	public static final String oriMoreThan = ">";
	public static final String whiteSpace = " ";
	
	public enum Fields{

        CREDIT_TYPE("Card type");
        private String desc;
        private String replacer;
        private String ucReplacer;

        private Fields(String desc) {
            this.desc = desc;
            this.replacer = escLessThan+desc+escMoreThan;
            this.ucReplacer = ucLessThan+desc+ucMoreThan;
        }

        public static Fields create(String value) {
            if(value!=null){
                String timmed = value.trim();
                for (Fields v : values()) {
                    if (v.getDesc().equalsIgnoreCase(timmed)) {
                        return v;
                    }
                }
            }
            throw new IllegalArgumentException();
        }

        /**
         * @return the desc
         */
        public String getDesc() {
            return desc;
        }
        
        public String getReplacer() {
            return replacer;
        }
        
        public String getUCReplacer() {
            return ucReplacer;
        }

        @Override
        public String toString() {
            return desc;
        }
    }
}	
