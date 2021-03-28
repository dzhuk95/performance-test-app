package com.performance.result.dao;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@Entity
@Table(name = "executed_queries")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExecutedQueries implements Persistable<Long>, Serializable {
    static final long serialVersionUID = -5280125841166807222L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SOME_SEQ")
    Long id;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    String query;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
