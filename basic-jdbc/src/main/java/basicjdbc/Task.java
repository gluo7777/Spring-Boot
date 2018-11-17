package basicjdbc;

import java.time.LocalTime;

public class Task {
	public static enum Type {
		GOOGLE, REG
	}

	private long id;
	private long taskId; // Google only
	private String taskName;
	private long listId; // Google only
	private String listName;
	private LocalTime time;
	private String notes;
	private String type;
	private long accountId;

	public final long getId() {
		return id;
	}

	public final void setId(long id) {
		this.id = id;
	}

	// tags
	public final long getTaskId() {
		return taskId;
	}

	public final void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public final String getTaskName() {
		return taskName;
	}

	public final void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public final long getListId() {
		return listId;
	}

	public final void setListId(long listId) {
		this.listId = listId;
	}

	public final String getListName() {
		return listName;
	}

	public final void setListName(String listName) {
		this.listName = listName;
	}

	public final LocalTime getTime() {
		return time;
	}

	public final void setTime(LocalTime time) {
		this.time = time;
	}

	public final String getNotes() {
		return notes;
	}

	public final void setNotes(String notes) {
		this.notes = notes;
	}

	public final String getType() {
		return type;
	}

	public final void setType(Type type) {
		this.type = type.toString().toLowerCase();
	}

	public final long getAccountId() {
		return accountId;
	}

	public final void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", taskId=" + taskId + ", taskName=" + taskName + ", listId=" + listId + ", listName="
				+ listName + ", time=" + time + ", notes=" + notes + ", type=" + type + ", accountId=" + accountId
				+ "]";
	}

}
