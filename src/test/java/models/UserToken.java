package models;

import com.fasterxml.jackson.annotation.JsonProperty;


    public class UserToken {
        @JsonProperty("success")
        private boolean success;

        @JsonProperty("accessToken")
        private String accessToken;

        @JsonProperty("refreshToken")
        private String refreshToken;

        @JsonProperty("user")
        private User user;

        // Геттеры и сеттеры

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }


        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public User getUser () {
            return user;
        }

        public void setUser (User user) {
            this.user = user;
        }
    }