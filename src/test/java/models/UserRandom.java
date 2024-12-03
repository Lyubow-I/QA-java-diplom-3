package models;


import org.apache.commons.lang3.RandomStringUtils;

    public class UserRandom {

        private String name;
        private String email;
        private String password;

        public UserRandom(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

       public String getPassword() {
          return password;
        }
        public static UserRandom getUser() {
            String name = RandomStringUtils.randomAlphabetic(6);
            String email = RandomStringUtils.randomAlphabetic(6) + "@ya.ru";
            String password = RandomStringUtils.randomAlphabetic(6);
            return new UserRandom(name, email, password);
        }

    }
