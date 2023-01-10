
## User
    id
    username 
    password
    emailAddress
    createdDate


  addTask(), 
  solveTask(), //or completeTask()
  removeTask()
  assignTask()


//


## Task 
    id
    title
    description
    dueDate
    statusTask [creted, requiresInfo, toFollowUp, solved]
    priorityLevel
    boolean completed; 
    assignedTo // user responsible for completing the task 

    markSolved()
    assignTask()
    

//
//This entity would represent the projects that tasks can be organized into
## Project
    id
    projectName
    description
    dueDate

    addTask() - create new tasks and add to project
    getTasks() - return list of tasks in the project
    deleteTask() - delete task from project





## Users Groups 
    id
    name
    description
    users [a list of user objects that are memebrs of the group]
    permissions [a list of permissions that are granted to the group]

    addUser(), 
    removeUser()

//


//What permissions does a group of users have? 
## Permissions
    id
    name 
    description
    roles [list of role objects that have been granted this permission]

    grantPermission(), //provide a list of users with this permission
    revokePermission() // revoke permission from a specific user 
//


//What roles can a user have? 
## Roles 
    id
    name
    description 
    permission []
    users [ a list of user objects that have been assigned this role]

    assignUser(), revokePermission()?



////Message to users as reminders 
## Notification
    id int
    type  string // reminder or notification
    message string // text of the message
    recipient: User // who will receive the notification
    dateSent: date // date when the notification was created and sent

    sendTo()


