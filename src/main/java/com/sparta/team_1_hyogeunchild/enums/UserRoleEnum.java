package com.sparta.team_1_hyogeunchild.enums;


public enum UserRoleEnum {
    BUYER(Authority.BUYER),  // 사용자 권한
    SELLER(Authority.SELLER),  // 판매자 권한
    ADMIN(Authority.ADMIN);  // 관리자 권한
    private final String authority;
    UserRoleEnum(String authority) {
        this.authority = authority;
    }
    public String getAuthority() {
        return this.authority;
    }
    public static class Authority {
        public static final String BUYER  = "ROLE_BUYER";
        public static final String SELLER = "ROLE_SELLER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}