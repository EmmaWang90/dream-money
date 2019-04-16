package com.wangdan.dream.persistence.orm.filter;

public enum FilterGroupType {
    AND {
        @Override
        public boolean calculate(boolean result, boolean tempResult) {
            return result && tempResult;
        }
    }, OR {
        @Override
        public boolean calculate(boolean result, boolean tempResult) {
            return result || tempResult;
        }
    }, NOT {
        @Override
        public boolean calculate(boolean result, boolean tempResult) {
            return result && !tempResult;
        }
    };

    public abstract boolean calculate(boolean result, boolean tempResult);
}
