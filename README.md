PuyoPuyoGame [Java Software Edition 1.7]
========================================

//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////PLAYERS/////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

This is a simple PuyoGame.
The goal of this game is to link puyos of the same color to increase your score (4+).
Possible key actions:
	-Left arrow: left shift
	-Right arrow: right shift
	-A: left rotation
	-D: right rotation
	-Space bar: drop active puyos
	-M: mute sound
	-Escape: pause the game
	

//////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////DEVELOPERS////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////

Project built with Maven.
All classes and methods are commented. Please see the JavaDoc.
All arts (images & sounds) are created by myself.
Swing Lib is used for graphics but the game can be easily updated with a 2D game engine due to
its generic architecture (please see UML class diagram). 

The way I designed this game was following the "modularity" philosophy.
Consequently, all key-tasks of the game (logic, display, inputs ...) have been separated in different modules
to make the game as generic as possible (MVC pattern for example here).
If design philosophy was "optimization" it is easy to merge some modules and use
simpler data structures.

