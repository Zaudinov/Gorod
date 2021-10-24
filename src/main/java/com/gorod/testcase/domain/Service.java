package com.gorod.testcase.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "service_id")
    private int id;

    private String name;


    @ManyToOne()
    @JoinColumn(name = "parent_id")
    private Service parent;

//    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
//    private Set<Service> children;

//    @ManyToMany(mappedBy = "services")
//    private Set<Subscriber> subscribers;

    public Service(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Service() {
    }



    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" +
                ", parent=" + parent +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<Service> getChildren() {
//        return Collections.unmodifiableSet(this.children);
//    }
//
//    public void setChildren(Set<Service> children) {
//        this.children = children;
//    }

    public Service getParent() {
        return parent;
    }

    public void setParent(Service parent) {
        this.parent = parent;
    }

//    public Set<Subscriber> getSubscribers() {
//        return subscribers;
//    }
//
//    public void setSubscribers(Set<Subscriber> subscribers) {
//        this.subscribers = subscribers;
//    }


}
