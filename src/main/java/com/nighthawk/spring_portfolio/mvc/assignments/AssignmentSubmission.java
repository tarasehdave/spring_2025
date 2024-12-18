package com.nighthawk.spring_portfolio.mvc.assignments;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nighthawk.spring_portfolio.mvc.person.Person;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AssignmentSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    @JsonBackReference
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Person student;

    private String content;
    private Double grade;
    private String feedback;

    private String comment;

    private long assignmentid;
    
    public AssignmentSubmission(Assignment assignment, Person student, String content, String comment) {
        this.assignment = assignment;
        this.student = student;
        this.content = content;
        this.grade = null;
        this.feedback = null;
        this.comment = comment;
        this.assignmentid=assignment.getId();
    }
}