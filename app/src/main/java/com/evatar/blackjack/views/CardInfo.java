package com.evatar.blackjack.views;

import java.io.Serializable;

public class CardInfo implements Serializable {
        public int num;
        public int imgID;
        public String name;
        public int price;

        public void setNum(int num) {
                this.num = num;
        }
        public int getNum() {
                return num;
        }

        public void setImgID(int imgID) {
                this.imgID = imgID;
        }
        public int getImgID() {
                return imgID;
        }

        public void setName(String name) {
                this.name = name;
        }
        public String getName() {
                return name;
        }

        public void setPrice(int price) {
                this.price = price;
        }
        public int getPrice() {
                return price;
        }
}
