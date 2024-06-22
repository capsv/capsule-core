package org.capsule.com.statistics.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "statistic")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistic implements CEntity{

    @Id
    @Column(name = "statistic_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "score")
    private int score;

    @Column(name ="completed_tasks")
    private int completedTasks;

    @Column(name = "missed_tasks")
    private int missedTasks;
}
