package com.gorod.testcase.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "service_id")
    private int id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id", referencedColumnName="service_id")
    private Set<Service> children;

    @Column(name="parent_id", nullable = true)
    private Integer parent;


    public Service(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Service() {
    }

    public Set<Service> getChildren() {
        return children;
    }

    public void setChildren(Set<Service> children) {
        this.children = children;
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

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return id == service.id &&
                name.equals(service.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
