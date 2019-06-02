package com.wangdan.dream.persistence.orm.filter;

public enum FilterType {
    EQUAL("=") {
        @Override
        public boolean check(Object fieldValue, Object validValue) {
            if (fieldValue == null && validValue == null)
                return true;
            else if (fieldValue == null || validValue == null)
                return false;
            else
                return fieldValue.equals(validValue);
        }
    }, LARGER_THAN(">") {
        @Override
        public boolean check(Object fieldValue, Object validValue) {
            return false;
        }
    }, LESS_THAN("<") {
        @Override
        public boolean check(Object fieldValue, Object validValue) {
            return false;
        }
    }, LARGER_OR_EQUAL(">=") {
        @Override
        public boolean check(Object fieldValue, Object validValue) {
            return false;
        }
    }, LESS_OR_EQUAL("<=") {
        @Override
        public boolean check(Object fieldValue, Object validValue) {
            return false;
        }
    }, NOT_EQUAL("!=") {
        @Override
        public boolean check(Object fieldValue, Object validValue) {
            return false;
        }
    }, LIKE("like ") {
        @Override
        public boolean check(Object fieldValue, Object validValue) {
            return false;
        }
    };
    private String value;

    private FilterType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    public abstract boolean check(Object fieldValue, Object validValue);

}
