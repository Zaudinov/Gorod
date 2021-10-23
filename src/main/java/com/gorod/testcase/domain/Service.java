package com.gorod.testcase.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "service")
@SecondaryTable(name="service_relations")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "service_id")
    private int id;

    private String name;

    @Column(table = "service_relations", name = "child_id")
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private List<Service> children;

    @ManyToOne
    @JoinColumn
    private Service parent;

    @ManyToOne
    @JoinColumn(name="subscriber_id")
    private Subscriber subscriber;

    public Service(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Service() {
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" + children +
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

    public List<Service> getChildren() {
        return children;
    }

    public void setChildren(List<Service> children) {
        this.children = children;
    }

    public Service getParent() {
        return parent;
    }

    public void setParent(Service parent) {
        this.parent = parent;
    }
}
