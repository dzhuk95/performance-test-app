package com.performance.result.dao;

import com.performance.result.dto.DatabaseType;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.domain.Persistable;

@Getter
@Setter
@Entity
@Table(name = "execution_results")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NamedEntityGraphs(value = @NamedEntityGraph(name = "executionResults.query", attributeNodes = {
        @NamedAttributeNode("query")}))
public class ExecutionResult implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long executionTimeDelta;

    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "query_id")
    @ManyToOne(fetch = FetchType.LAZY)
    ExecutedQueries query;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    DatabaseType databaseType;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
