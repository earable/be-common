package ai.earable.platform.common.data.program.user.enums;

public enum UserProgramState {

    JOIN,         /* When the user starts joining a program */
    DOING,        /* When the user begins doing a program   */
    INCOMPLETE,   /* When user program do not invalid       */
    COMPLETED,    /* When user did complete a program       */
    CANCEL        /* When user cancel the program           */

    /*
    * On-going: when user_program_state = DOING
    * Completed: when user_program_state = COMPLETED
    * Upcoming: when get latest program from ABS and ignore program_id status = JOIN | DOING | INCOMPLETE | COMPLETED.
    * */
}
