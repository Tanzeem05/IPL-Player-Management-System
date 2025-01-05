package data;

import network.dto.PlayerEditInfo;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable {
    String name;
    String country;
    Club club;
    String clubName;
    int age;
    int number;
    String position;
    double height;
    double salary;
    boolean isBeingSold = false;
    int id;
    double price=-1;

    public String getClubName(){return clubName;}
    public void setClubName(String clubName) {this.clubName = clubName;}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public boolean isBeingSold() {
        return isBeingSold;
    }

    public void setBeingSold(boolean beingSold) {
        isBeingSold = beingSold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Player(Player p) {
        name = p.name;
        country = p.country;
        club = p.club;
        clubName=p.getClub().getName();
        age = p.age;
        number = p.number;
        position = p.position;
        height = p.height;
        salary = p.salary;
        isBeingSold = p.isBeingSold;
        id = p.id;
        price = p.price;
    }

    public Player(String name, String country, Club club, int age,double height, int number,  double salary) {
        this.name = name;
        this.country = country;
        this.club = club;
        this.age = age;
        this.number = number;
        this.position = "Batsman";
        this.salary = salary;
        this.isBeingSold = false;
        id = CentralDatabase.getInstance().players.size()+1;
        price = 0;
        this.height = height;
        clubName=club.getName();
    }

    public Player(String name,String country,int age,double height,String club,String pos,int number,double salary)
    {
        this.name=name;
        this.country=country;
        this.age=age;
        this.height=height;
        this.club=new Club(club);
        this.position=pos;
        this.number=number;
        this.salary=salary;
        this.isBeingSold=false;
        clubName=club;
        id=CentralDatabase.getInstance().players.size()+1;
        price=0;
    }

    public void edit(PlayerEditInfo p){
        name = p.getName();
        country = p.getCountry();
        position = p.getPosition();
        age = p.getAge();
        number = p.getNumber();
        height = p.getHeight();
        salary = p.getSalary();
    }

    public Player(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name) && country.equals(player.country) && position.equals(player.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, position);
    }

    public void show()
    {
        System.out.println(id);
    }

}
