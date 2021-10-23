package com.gorod.testcase.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subscriber")
public class Subscriber {
    @Id
    @Column(name="subscriber_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String fio;

    @Column(unique = true)
    private String account;


//    @OneToMany(mappedBy = "subscriber")
//    private List<Service> services;

    public Subscriber() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Subscriber(String fio) {
        this.fio = fio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

//    public List<Service> getServices() {
//        return services;
//    }
//
//    public void setServices(List<Service> services) {
//        this.services = services;
//    }
}
