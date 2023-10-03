package hello.itemservice.domain.item;

public enum ItemType {

    Book("도서"), ETC("기타"), FOOD("음식");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
