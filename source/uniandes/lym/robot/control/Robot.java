/* Robot.java */
/* Generated By:JavaCC: Do not edit this line. Robot.java */
package uniandes.lym.robot.control;

import uniandes.lym.robot.kernel.*;
import uniandes.lym.robot.view.Console;

import java.awt.Point;
import java.io.*;
import java.util.Vector;
import java.util.LinkedList;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.lang.Integer;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Robot implements RobotConstants {

        public static Map<Integer, Map<String, Integer>> variablesForLevel = new HashMap<>();
        public static int currentLevel = 0;
        public static ArrayList<String> currentMacroParameters = new ArrayList<String>();
        public static boolean inMacroDefinition = false;
        public static Map<String, Integer> macroParametersQuantity = new HashMap<>();
        public static String currentMacroNameInMacroDefinition;
        public static boolean receivingMacroParameters = false;
        public static boolean inVariableAssignment = false;
        public static String currentMacroNameRecievingParameters;

        private RobotWorldDec robotWorld;



        void setWorld(RobotWorld w) {
                robotWorld = (RobotWorldDec) w;
        }

        String salida=new String();

//boolean command(uniandes.lym.robot.view.Console sistema) :
  final public boolean command(Console sistema) throws ParseException {int x,y;
        salida=new String();
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EXEC:
    case NEW:{
      label_1:
      while (true) {
        input();
try {
                                        Thread.sleep(900);
                        } catch (InterruptedException e) {
                                                System.err.format("IOException: %s%n", e);
                                }

                sistema.printOutput(salida);
                {if ("" != null) return true;}
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case EXEC:
        case NEW:{
          ;
          break;
          }
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
      }
      break;
      }
    case 0:{
      jj_consume_token(0);
{if ("" != null) return false;}
      break;
      }
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public void input() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EXEC:{
      executionCommand();
      break;
      }
    case NEW:{
      definition();
      break;
      }
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void executionCommand() throws ParseException {
    jj_consume_token(EXEC);
    B();
}

  final public void definition() throws ParseException {
    jj_consume_token(NEW);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case VAR:{
      varDefinition();
      break;
      }
    case MACRO:{
      macroDefinition();
      break;
      }
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void varDefinition() throws ParseException {String variableName;
  int variableValue;
    jj_consume_token(VAR);
    jj_consume_token(NAME);
variableName = token.image.toLowerCase();
    jj_consume_token(EQUAL);
    variableValue = n(true);
if(!Robot.variablesForLevel.containsKey(Robot.currentLevel)) {
                  Map<String, Integer> internalMap = new HashMap<>();
                  Robot.variablesForLevel.put(Robot.currentLevel, internalMap);
                }
                Map<String, Integer> internalMap = Robot.variablesForLevel.get(Robot.currentLevel);
                if(internalMap.containsKey(variableName)) {
                  {if (true) throw new Error("Variable " + variableName + " has been already declared.");}
                }else {
                  internalMap.put(variableName, variableValue);
                  Robot.variablesForLevel.put(Robot.currentLevel, internalMap);
                  System.out.println(variablesForLevel);
                }
}

  final public void macroDefinition() throws ParseException {
    jj_consume_token(MACRO);
Robot.inMacroDefinition = true;
    jj_consume_token(NAME);
if(Robot.macroParametersQuantity.containsKey(token.image)) {if (true) throw new Error("There is a macro already declared with the name '" + token.image + "'.");}
                Robot.currentMacroNameInMacroDefinition = token.image;
    jj_consume_token(LEFT_PARENTEHSIS);
    params();
    jj_consume_token(RIGHT_PARENTEHSIS);
Robot.inMacroDefinition = false; Robot.currentMacroNameInMacroDefinition = null;
    B();
Robot.currentMacroParameters = new ArrayList<String>(); System.out.println(Robot.currentMacroParameters);
}

  final public Integer n(boolean inVariableDefinition) throws ParseException {int constantValue;
  int valueInVariable = 0;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NUMBER:{
      jj_consume_token(NUMBER);
if(inVariableDefinition) {if ("" != null) return Integer.parseInt(token.image);}
                        else if(Robot.inVariableAssignment) {if ("" != null) return Integer.parseInt(token.image);}
                        else if(Robot.inMacroDefinition) {if (true) throw new Error("Numbers can not be parameters.");}
                        else if(Robot.receivingMacroParameters) {
                                Robot.macroParametersQuantity.put(Robot.currentMacroNameRecievingParameters, Robot.macroParametersQuantity.get(Robot.currentMacroNameRecievingParameters)-1);
                                {if ("" != null) return Integer.parseInt(token.image);}
                        }
                        {if ("" != null) return null;}
      break;
      }
    case NAME:{
      jj_consume_token(NAME);
String variableName = token.image.toLowerCase();
                        boolean found = false;

                        // Para la definicion de una variable: devuelve el valor de la variable si esta en una definicion de variable, solo lo busca en las variables globales porque dentro de un exec o funcion no se pueden definir variables
                        if(inVariableDefinition) {
                                for(int i = Robot.currentLevel; i>=0; i--) {
                                        Map<String, Integer> variablesInCurrentLevel = Robot.variablesForLevel.get(i);
                                        if(variablesInCurrentLevel != null && variablesInCurrentLevel.containsKey(variableName)) {
                                          found = true;
                                          valueInVariable = variablesInCurrentLevel.get(variableName);
                                        }
                                }
                                if(found) {if ("" != null) return valueInVariable;}
                                else {if (true) throw new Error("The variable '" + variableName + "' used in the assignment was not declared before. ");}
                        }

                        // Si esta en la asignacion de una variable la busca en las variables globales, locales y parametros
                        else if(Robot.inVariableAssignment) {
                                for(int i = Robot.currentLevel; i>=0; i--) {
                                        Map<String, Integer> variablesInCurrentLevel = Robot.variablesForLevel.get(i);
                                        if(variablesInCurrentLevel != null && variablesInCurrentLevel.containsKey(variableName)) {
                                          found = true;
                                          valueInVariable = variablesInCurrentLevel.get(variableName);
                                        }
                                }
                                boolean foundInMacroParameters = false;
                                for(String element: Robot.currentMacroParameters) {
                                        if (element.equals(variableName)) {
                                                foundInMacroParameters = true;
                                        }
                                }
                                if(found || foundInMacroParameters) {if ("" != null) return valueInVariable;}
                                else {if (true) throw new Error("The variable '" + variableName + "' used in the assignment was not declared before. ");}
                        }

                        // Si se estan definiendo los parametros de una macro: se añade a la lista de variables actuales de la macro
                        else if(Robot.inMacroDefinition) {
                                if(!Robot.currentMacroParameters.contains(token.image)) {

                                  if(macroParametersQuantity.containsKey(Robot.currentMacroNameInMacroDefinition))      Robot.macroParametersQuantity.put(Robot.currentMacroNameInMacroDefinition, Robot.macroParametersQuantity.get(Robot.currentMacroNameInMacroDefinition)+1);
                                  else Robot.macroParametersQuantity.put(Robot.currentMacroNameInMacroDefinition, 1);

                                  Robot.currentMacroParameters.add(token.image);
                                }
                                else {if (true) throw new Error("The parameter with the name " + token.image + " is already declared for this macro.");}
                        }

                        // Si se esta invocando una macro: para detectar que se pase la cantidad correcta de argumentos y ademas verificar que se encuentre la variable definida sea como variable global o local
                        else if(Robot.receivingMacroParameters) {
                                for(int i = Robot.currentLevel; i>=0; i--) {
                                        Map<String, Integer> variablesInCurrentLevel = Robot.variablesForLevel.get(i);
                                        if(variablesInCurrentLevel != null && variablesInCurrentLevel.containsKey(variableName)) {
                                          found = true;
                                          valueInVariable = variablesInCurrentLevel.get(variableName);
                                        }
                                }
                                boolean foundInMacroParameters = false;
                                for(String element: Robot.currentMacroParameters) {
                                        if (element.equals(variableName)) {
                                                foundInMacroParameters = true;
                                        }
                                }
                                if(found || foundInMacroParameters) {

                                Robot.macroParametersQuantity.put(Robot.currentMacroNameRecievingParameters, Robot.macroParametersQuantity.get(Robot.currentMacroNameRecievingParameters)-1);
                                  {if ("" != null) return valueInVariable;}
                                }
                                else {if (true) throw new Error("The argument '" + variableName + "' was not declared before. ");}
                        }


                        {if ("" != null) return null;}
      break;
      }
    case SIZE:
    case MY_X:
    case MY_Y:
    case MY_CHIPS:
    case MY_BALLOONS:
    case BALLOONS_HERE:
    case CHIPS_HERE:
    case ROOM_FOR_CHIPS:{
      constantValue = constant();
if(inVariableDefinition) {if ("" != null) return constantValue;}
                        else if(Robot.inVariableAssignment) {if ("" != null) return constantValue;}
                        else if(Robot.inMacroDefinition) {if (true) throw new Error("Constants can not be parameters.");}
                        else if(Robot.receivingMacroParameters) {
                                Robot.macroParametersQuantity.put(Robot.currentMacroNameRecievingParameters, Robot.macroParametersQuantity.get(Robot.currentMacroNameRecievingParameters)-1);
                                {if ("" != null) return constantValue;}
                        }
                        {if ("" != null) return null;}
      break;
      }
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public int constant() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case SIZE:{
      jj_consume_token(SIZE);
{if ("" != null) return robotWorld.getN();}
      break;
      }
    case MY_X:{
      jj_consume_token(MY_X);
{if ("" != null) return (int) robotWorld.getPosition().getX();}
      break;
      }
    case MY_Y:{
      jj_consume_token(MY_Y);
{if ("" != null) return (int) robotWorld.getPosition().getY();}
      break;
      }
    case MY_CHIPS:{
      jj_consume_token(MY_CHIPS);
{if ("" != null) return robotWorld.getMyChips();}
      break;
      }
    case MY_BALLOONS:{
      jj_consume_token(MY_BALLOONS);
{if ("" != null) return robotWorld.getMyBalloons();}
      break;
      }
    case BALLOONS_HERE:{
      jj_consume_token(BALLOONS_HERE);
{if ("" != null) return robotWorld.countBalloons();}
      break;
      }
    case CHIPS_HERE:{
      jj_consume_token(CHIPS_HERE);
{if ("" != null) return 0;} /*TODO*/
      break;
      }
    case ROOM_FOR_CHIPS:{
      jj_consume_token(ROOM_FOR_CHIPS);
{if ("" != null) return robotWorld.getMyChips();}
      break;
      }
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
}

  final public void params() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case SIZE:
    case MY_X:
    case MY_Y:
    case MY_CHIPS:
    case MY_BALLOONS:
    case BALLOONS_HERE:
    case CHIPS_HERE:
    case ROOM_FOR_CHIPS:
    case NUMBER:
    case NAME:{
      n(false);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
        case COMMA:{
          ;
          break;
          }
        default:
          jj_la1[6] = jj_gen;
          break label_2;
        }
        jj_consume_token(COMMA);
        n(false);
      }
      break;
      }
    default:
      jj_la1[7] = jj_gen;
      ;
    }
}

  final public void B() throws ParseException {
    jj_consume_token(LEFT_BRACE);
Robot.currentLevel++;
    label_3:
    while (true) {
      instruction();
      jj_consume_token(SEMICOLON);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case MOVE:
      case RIGHT:
      case PUT:
      case PICK:
      case POP:
      case GO:
      case HOP:
      case TURN_TO_MY:
      case TURN_TO_THE:
      case WALK:
      case JUMP:
      case DROP:
      case GRAB:
      case LET_GO:
      case MOVES:
      case NOP:
      case SAFE_EXE:
      case IF:
      case DO:
      case REP:
      case NAME:{
        ;
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        break label_3;
      }
    }
    jj_consume_token(RIGHT_BRACE);
Robot.currentLevel--;
}

  final public void instruction() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MOVE:
    case RIGHT:
    case PUT:
    case PICK:
    case POP:
    case GO:
    case HOP:
    case TURN_TO_MY:
    case TURN_TO_THE:
    case WALK:
    case JUMP:
    case DROP:
    case GRAB:
    case LET_GO:
    case MOVES:
    case NOP:
    case SAFE_EXE:
    case NAME:{
      commandGroup();
      break;
      }
    case IF:
    case DO:
    case REP:{
      controlStructure();
      break;
      }
    default:
      jj_la1[9] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void commandGroup() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case MOVE:{
      move();
      break;
      }
    case RIGHT:{
      right();
      break;
      }
    case PUT:{
      put();
      break;
      }
    case PICK:{
      pick();
      break;
      }
    case POP:{
      pop();
      break;
      }
    case HOP:{
      hop();
      break;
      }
    case GO:{
      go();
      break;
      }
    case NAME:{
      assignmentOrMacroInvocation();
      break;
      }
    case TURN_TO_MY:{
      turnToMy();
      break;
      }
    case TURN_TO_THE:{
      turnToThe();
      break;
      }
    case WALK:{
      walk();
      break;
      }
    case JUMP:{
      jump();
      break;
      }
    case DROP:{
      drop();
      break;
      }
    case GRAB:{
      grab();
      break;
      }
    case LET_GO:{
      letGo();
      break;
      }
    case MOVES:{
      moves();
      break;
      }
    case NOP:{
      jj_consume_token(NOP);
      break;
      }
    case SAFE_EXE:{
      safeExe();
      break;
      }
    default:
      jj_la1[10] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void move() throws ParseException {
    jj_consume_token(MOVE);
    jj_consume_token(LEFT_PARENTEHSIS);
    n(false);
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void right() throws ParseException {
    jj_consume_token(RIGHT);
    jj_consume_token(LEFT_PARENTEHSIS);
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void put() throws ParseException {
    jj_consume_token(PUT);
    jj_consume_token(LEFT_PARENTEHSIS);
    object();
    jj_consume_token(COMMA);
    n(false);
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void pick() throws ParseException {
    jj_consume_token(PICK);
    jj_consume_token(LEFT_PARENTEHSIS);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case BALLOONS:
    case CHIPS:{
      object();
      jj_consume_token(COMMA);
      n(false);
      break;
      }
    case SIZE:
    case MY_X:
    case MY_Y:
    case MY_CHIPS:
    case MY_BALLOONS:
    case BALLOONS_HERE:
    case CHIPS_HERE:
    case ROOM_FOR_CHIPS:
    case NUMBER:
    case NAME:{
      n(false);
      break;
      }
    default:
      jj_la1[11] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void pop() throws ParseException {
    jj_consume_token(POP);
    jj_consume_token(LEFT_PARENTEHSIS);
    n(false);
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void hop() throws ParseException {
    jj_consume_token(HOP);
    jj_consume_token(LEFT_PARENTEHSIS);
    n(false);
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void go() throws ParseException {
    jj_consume_token(GO);
    jj_consume_token(LEFT_PARENTEHSIS);
    n(false);
    jj_consume_token(COMMA);
    n(false);
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void object() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case CHIPS:{
      jj_consume_token(CHIPS);
      break;
      }
    case BALLOONS:{
      jj_consume_token(BALLOONS);
      break;
      }
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void assignmentOrMacroInvocation() throws ParseException {String macroName;
    jj_consume_token(NAME);
macroName = token.image;
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case EQUAL:{
      assignment();
      break;
      }
    case LEFT_PARENTEHSIS:{
      macroInvocation(macroName);
      break;
      }
    default:
      jj_la1[13] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void assignment() throws ParseException {
    jj_consume_token(EQUAL);
Robot.inVariableAssignment = true;
    n(false);
Robot.inVariableAssignment = false;
}

  final public void macroInvocation(String macroName) throws ParseException {int initialParameters = 0;
    jj_consume_token(LEFT_PARENTEHSIS);
Robot.receivingMacroParameters=true;
                Robot.currentMacroNameRecievingParameters = macroName;
                System.out.println(macroParametersQuantity);
                if(Robot.macroParametersQuantity.containsKey(Robot.currentMacroNameRecievingParameters)) initialParameters = Robot.macroParametersQuantity.get(Robot.currentMacroNameRecievingParameters);
                else {if (true) throw new Error("There is not a macro defined with the name '" + Robot.currentMacroNameRecievingParameters + "'.");}
    params();
    jj_consume_token(RIGHT_PARENTEHSIS);
System.out.println(macroParametersQuantity);
                if(Robot.macroParametersQuantity.containsKey(Robot.currentMacroNameRecievingParameters) && Robot.macroParametersQuantity.get(Robot.currentMacroNameRecievingParameters) != 0) {if (true) throw new Error("The macro invocation for "+ Robot.currentMacroNameRecievingParameters + " did not recieve the correct amount of arguments.");}
                if(Robot.macroParametersQuantity.containsKey(Robot.currentMacroNameRecievingParameters)) Robot.macroParametersQuantity.put(Robot.currentMacroNameRecievingParameters, initialParameters);
                Robot.receivingMacroParameters=false;
                Robot.currentMacroNameRecievingParameters = null;
}

  final public void turnToMy() throws ParseException {
    jj_consume_token(TURN_TO_MY);
    jj_consume_token(LEFT_PARENTEHSIS);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LEFT:{
      jj_consume_token(LEFT);
      break;
      }
    case RIGHT:{
      jj_consume_token(RIGHT);
      break;
      }
    case BACK:{
      jj_consume_token(BACK);
      break;
      }
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void turnToThe() throws ParseException {
    jj_consume_token(TURN_TO_THE);
    jj_consume_token(LEFT_PARENTEHSIS);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NORTH:{
      jj_consume_token(NORTH);
      break;
      }
    case SOUTH:{
      jj_consume_token(SOUTH);
      break;
      }
    case EAST:{
      jj_consume_token(EAST);
      break;
      }
    case WEST:{
      jj_consume_token(WEST);
      break;
      }
    default:
      jj_la1[15] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void walk() throws ParseException {
    jj_consume_token(WALK);
    jj_consume_token(LEFT_PARENTEHSIS);
    n(false);
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void jump() throws ParseException {
    jj_consume_token(JUMP);
    jj_consume_token(LEFT_PARENTEHSIS);
    n(false);
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void drop() throws ParseException {
    jj_consume_token(DROP);
    jj_consume_token(LEFT_PARENTEHSIS);
    n(false);
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void grab() throws ParseException {
    jj_consume_token(GRAB);
    jj_consume_token(LEFT_PARENTEHSIS);
    n(false);
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void letGo() throws ParseException {
    jj_consume_token(LET_GO);
    jj_consume_token(LEFT_PARENTEHSIS);
    n(false);
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void moves() throws ParseException {
    jj_consume_token(MOVES);
    jj_consume_token(LEFT_PARENTEHSIS);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case FORWARD:{
      jj_consume_token(FORWARD);
      break;
      }
    case RIGHT:{
      jj_consume_token(RIGHT);
      break;
      }
    case LEFT:{
      jj_consume_token(LEFT);
      break;
      }
    case BACKWARDS:{
      jj_consume_token(BACKWARDS);
      break;
      }
    default:
      jj_la1[16] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case COMMA:{
        ;
        break;
        }
      default:
        jj_la1[17] = jj_gen;
        break label_4;
      }
      jj_consume_token(COMMA);
      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case FORWARD:{
        jj_consume_token(FORWARD);
        break;
        }
      case RIGHT:{
        jj_consume_token(RIGHT);
        break;
        }
      case LEFT:{
        jj_consume_token(LEFT);
        break;
        }
      case BACKWARDS:{
        jj_consume_token(BACKWARDS);
        break;
        }
      default:
        jj_la1[18] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void safeExe() throws ParseException {
    jj_consume_token(SAFE_EXE);
    jj_consume_token(LEFT_PARENTEHSIS);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case WALK:{
      walk();
      break;
      }
    case JUMP:{
      jump();
      break;
      }
    case DROP:{
      drop();
      break;
      }
    case PICK:{
      pick();
      break;
      }
    case GRAB:{
      grab();
      break;
      }
    case LET_GO:{
      letGo();
      break;
      }
    case POP:{
      pop();
      break;
      }
    default:
      jj_la1[19] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void controlStructure() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IF:{
      iff();
      break;
      }
    case DO:{
      doo();
      break;
      }
    case REP:{
      rep();
      break;
      }
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void iff() throws ParseException {
    jj_consume_token(IF);
    jj_consume_token(LEFT_PARENTEHSIS);
    condition();
    jj_consume_token(RIGHT_PARENTEHSIS);
    jj_consume_token(THEN);
    B();
    jj_consume_token(ELSE);
    B();
    jj_consume_token(FI);
}

  final public void doo() throws ParseException {
    jj_consume_token(DO);
    jj_consume_token(LEFT_PARENTEHSIS);
    condition();
    jj_consume_token(RIGHT_PARENTEHSIS);
    B();
    jj_consume_token(OD);
}

  final public void rep() throws ParseException {
    jj_consume_token(REP);
    n(false);
    jj_consume_token(TIMES);
    B();
    jj_consume_token(PER);
}

  final public void condition() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case IS_BLOCKED:{
      isBlocked();
      break;
      }
    case IS_FACING:{
      isFacing();
      break;
      }
    case ZERO:{
      zero();
      break;
      }
    case NOT:{
      not();
      break;
      }
    default:
      jj_la1[21] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
}

  final public void isBlocked() throws ParseException {
    jj_consume_token(IS_BLOCKED);
    jj_consume_token(LEFT_PARENTEHSIS);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case LEFT:{
      jj_consume_token(LEFT);
      break;
      }
    case RIGHT:{
      jj_consume_token(RIGHT);
      break;
      }
    case FRONT:{
      jj_consume_token(FRONT);
      break;
      }
    case BACK:{
      jj_consume_token(BACK);
      break;
      }
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void isFacing() throws ParseException {
    jj_consume_token(IS_FACING);
    jj_consume_token(LEFT_PARENTEHSIS);
    switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
    case NORTH:{
      jj_consume_token(NORTH);
      break;
      }
    case SOUTH:{
      jj_consume_token(SOUTH);
      break;
      }
    case EAST:{
      jj_consume_token(EAST);
      break;
      }
    case WEST:{
      jj_consume_token(WEST);
      break;
      }
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void zero() throws ParseException {
    jj_consume_token(ZERO);
    jj_consume_token(LEFT_PARENTEHSIS);
    n(false);
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  final public void not() throws ParseException {
    jj_consume_token(NOT);
    jj_consume_token(LEFT_PARENTEHSIS);
    condition();
    jj_consume_token(RIGHT_PARENTEHSIS);
}

  /** Generated Token Manager. */
  public RobotTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[24];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static private int[] jj_la1_2;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	   jj_la1_init_2();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x400000,0x400001,0x400000,0x0,0x0,0x0,0x0,0x0,0x3fffe0,0x3fffe0,0x3fffe0,0x0,0x0,0x0,0x1800040,0x3c000000,0xc0800040,0x0,0xc0800040,0x7c300,0x0,0x0,0x3800040,0x3c000000,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x100,0x100,0x100,0x600,0xff,0xff,0x20000000,0xff,0x28800,0x28800,0x0,0x30000ff,0x3000000,0x4000000,0x0,0x0,0x0,0x20000000,0x0,0x0,0x28800,0xf00000,0x0,0x0,};
	}
	private static void jj_la1_init_2() {
	   jj_la1_2 = new int[] {0x0,0x0,0x0,0x0,0x14,0x0,0x0,0x14,0x10,0x10,0x10,0x14,0x0,0x1,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
	}

  /** Constructor with InputStream. */
  public Robot(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Robot(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new RobotTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Robot(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new RobotTokenManager(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new RobotTokenManager(jj_input_stream);
	}

	 token_source.ReInit(jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Robot(RobotTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(RobotTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 24; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[70];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 24; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		   if ((jj_la1_2[i] & (1<<j)) != 0) {
			 la1tokens[64+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 70; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
