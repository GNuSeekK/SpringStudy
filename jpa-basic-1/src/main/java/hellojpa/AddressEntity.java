package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "ADDRESS")
public class AddressEntity {

    public AddressEntity() {
    }

    public AddressEntity(String city, String street, String zipcode) {
        Address address = new Address(city, street, zipcode);
        this.address = address;
    }

    @Id
    @GeneratedValue
    private Long id;

    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
