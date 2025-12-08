package com.techelevator.model;

public class AvailablePet {
    private Long animalId;
    private String animalType;
    private String animalBreed;
    private String animalColor;
    private Integer animalAge;
    private String animalName;
    private String adoptionStatus;
    private String imageUrl;
    private String imageUrl1;
    private String imageUrl2;

    //Add a no-arguments and all-arguments constructor for convenience
    public AvailablePet() {}

    public AvailablePet(Long animalId, String animalType, String animalBreed, String animalColor,
                        Integer animalAge, String animalName, String adoptionStatus,
                        String imageUrl, String imageUrl1, String imageUrl2) {
        this.animalId = animalId;
        this.animalType = animalType;
        this.animalBreed = animalBreed;
        this.animalColor = animalColor;
        this.animalAge = animalAge;
        this.animalName = animalName;
        this.adoptionStatus = adoptionStatus;
        this.imageUrl = imageUrl;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
    }

    public Long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getAnimalBreed() {
        return animalBreed;
    }

    public void setAnimalBreed(String animalBreed) {
        this.animalBreed = animalBreed;
    }

    public String getAnimalColor() {
        return animalColor;
    }

    public void setAnimalColor(String animalColor) {
        this.animalColor = animalColor;
    }

    public Integer getAnimalAge() {
        return animalAge;
    }

    public void setAnimalAge(Integer animalAge) {
        this.animalAge = animalAge;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getAdoptionStatus() {
        return adoptionStatus;
    }

    public void setAdoptionStatus(String adoptionStatus) {
        this.adoptionStatus = adoptionStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }
}
