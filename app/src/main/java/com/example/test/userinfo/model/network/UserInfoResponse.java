package com.example.test.userinfo.model.network;
import com.example.test.userinfo.model.db.User;

import java.io.Serializable;
import java.util.List;

public class UserInfoResponse implements Serializable{
    public List<Results> results;
    public Results getResults(User user) {
        Results userInfo = new UserInfoResponse.Results();
        userInfo.setGender(user.getGender());
        userInfo.setPhone(user.getPhone());
        userInfo.setCell(user.getCell());
        userInfo.setNat(user.getNat());
        userInfo.setEmail(user.getEmail());

        Name name = new Name();
        name.setFirstName(user.getFirstName());
        name.setLastName(user.getLastName());
        name.setTitle(user.getTitle());

        Location location = new Location();
        location.setCity(user.getCity());
        location.setState(user.getState());
        location.setStreet(user.getStreet());

        Picture picture = new Picture();
        picture.setLargeImage(user.getLargeImage());

        userInfo.setPicture(picture);
        userInfo.setLocation(location);
        userInfo.setName(name);

        return userInfo;
    }

    public class Results implements Serializable {
        private String gender;
        private String phone;
        private String cell;
        private String nat;
        private Picture picture;
        private Location location;
        private Name name;
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCell() {
            return cell;
        }

        public void setCell(String cell) {
            this.cell = cell;
        }

        public String getNat() {
            return nat;
        }

        public void setNat(String nat) {
            this.nat = nat;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Name getName() {
            return name;
        }

        public String getFullName() {
            return name.title + " " + name.first + " " + name.last;
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Picture getPicture() {
            return picture;
        }

        public void setPicture(Picture picture) {
            this.picture = picture;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }


    }
    public class Picture implements Serializable {
        private String large;

        public String getLargeImage() {
            return large;
        }

        public void setLargeImage(String large) {
            this.large = large;
        }

    }
    public class Location implements Serializable {
        private String street;
        private String city;
        private String state;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    public class Name implements Serializable {
        private String title;
        private String first;
        private String last;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFirstName() {
            return first;
        }

        public void setFirstName(String first) {
            this.first = first;
        }

        public String getLastName() {
            return last;
        }

        public void setLastName(String last) {
            this.last = last;
        }

    }
}


