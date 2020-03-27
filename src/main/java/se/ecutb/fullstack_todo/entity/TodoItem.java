package se.ecutb.fullstack_todo.entity;

import org.hibernate.annotations.CascadeType;
import org.hibernate.engine.internal.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
public class TodoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;
    private String itemTitle;
    private String itemDescription;
    private LocalDate deadline;
    private boolean doneStatus;
    private double reward;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser userName;

    public TodoItem(String itemTitle, String itemDescription, LocalDate deadline, boolean doneStatus, double reward) {
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.deadline = deadline;
        this.doneStatus = doneStatus;
        this.reward = reward;
    }

    public TodoItem() {}

    public int getItemId() {
        return itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isDoneStatus() {
        return doneStatus;
    }

    public void setDoneStatus(boolean doneStatus) {
        this.doneStatus = doneStatus;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItem todoItem = (TodoItem) o;
        return itemId == todoItem.itemId &&
                doneStatus == todoItem.doneStatus &&
                Double.compare(todoItem.reward, reward) == 0 &&
                Objects.equals(itemTitle, todoItem.itemTitle) &&
                Objects.equals(itemDescription, todoItem.itemDescription) &&
                Objects.equals(deadline, todoItem.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemTitle, itemDescription, deadline, doneStatus, reward);
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "itemId=" + itemId +
                ", itemTitle='" + itemTitle + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", deadline=" + deadline +
                ", doneStatus=" + doneStatus +
                ", reward=" + reward +
                '}';
    }

}


