grammar Dasm;

@header {
package org.middlepath.dassembler;
}

fragment A : ('A'|'a');
fragment B : ('B'|'b');
fragment C : ('C'|'c');
fragment D : ('D'|'d');
fragment E : ('E'|'e');
fragment F : ('F'|'f');
fragment I : ('I'|'i');
fragment J : ('J'|'j');
fragment L : ('L'|'l');
fragment M : ('M'|'m');
fragment O : ('O'|'o');
fragment P : ('P'|'p');
fragment Q : ('Q'|'q');
fragment R : ('R'|'r');
fragment S : ('S'|'s');
fragment T : ('T'|'t');
fragment U : ('U'|'u');
fragment X : ('X'|'x');

fragment COMMA : ',';
fragment LOWERCASE: [a-z]+;
fragment UPPERCASE: [A-Z]+;
fragment NUM: [0-9]+;

fragment BIN_PREFIX
	:	'0' B
	;
	
fragment HEX_PREFIX
	:	'0' X
	;
	
app
	:	(line)* EOF
	;

line
	:	((DECIMAL_NUMBER instruction (operand)? (COMMENT)?)) | NEWLINE | COMMENT
	;
	
instruction
	:	(reference_instruction|immediate_instruction|nooperand_instruction)
	;
	
reference_instruction
	:	(STA|LDA|LDB|STB|JMP|ADD|SUB|BEQ|OUT) REFERENCE_SYMBOL
	;
	
immediate_instruction
	:	(STA|LDA|LDB|STB|JMP|ADD|SUB|BEQ|OUT) REFERENCE_SYMBOL
	;
	
nooperand_instruction
	:	(ADD|SUB)
	;
	
operand
	:	(HEX_NUMBER | BIN_NUMBER | DECIMAL_NUMBER)
	;
	
AB
	:	A COMMA B
	;
	
COMMENT
	:	SEMICOLON ~[\r\n]* -> channel(HIDDEN)
	;
	
REFERENCE_SYMBOL
	:	{_input.LA(-1) == ' '}? POUND
	;
	
IMMEDIATE_SYMBOL
	:	{_input.LA(-1_ == ' '}? DOLLAR
	;
	
SEMICOLON
	:	';'
	;
	
BIN_NUMBER
	:	BIN_PREFIX ('0' | '1')+
	;
	
HEX_NUMBER
	:	HEX_PREFIX (NUM|A|B|C|D|E|F)+
	;
	
DECIMAL_NUMBER
	:	{_input.LA(-1) != 'X' && _input.LA(-1) != 'b'}? NUM
	;
	
DOLLAR
	:	'$'
	;
	
POUND
	:	'#'
	;
	
STA : S T A ;
STB : S T B ;
LDA : L D A ;
LDB : L D B ;
JMP : J M P ;
ADD : A D D ;
SUB : S U B ;
BEQ : B E Q ;
OUT : O U T ;

WS
	:	(' '|'\t')+ -> skip
	;
	
NEWLINE
	:	(('\r'? '\n') | '\r')+
	;
	
	
	