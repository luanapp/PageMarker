package br.org.eldorado.pagemarker.persistence.vo;


public class User {
        private String name;
        private byte[] picture;
        
        public String getName() {
                return name;
        }
        
        public void setName(String name) {
                this.name = name;
        }
        
        public byte[] getPicture() {
                return picture;
        }
        
        public void setPicture(byte[] picture) {
                this.picture = picture;
        }
}
