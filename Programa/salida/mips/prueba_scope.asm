.data

# ---------- variables del programa ----------
var_numero: .word 0
var_g: .word 0
var_ff: .float 0.0

# ---------- numeros float fijos ----------
num_1: .float 4.4
num_2: .float 9.9
num_3: .float 6.7
num_4: .float -0.01
num_5: .float 5.5
num_6: .float 6.4
num_7: .float 7.7
num_8: .float 8.8
num_9: .float 5.6
num_10: .float 44.55
num_11: .float 444.555
num_12: .float -5.6
num_13: .float 56.6
num_14: .float 8.9

# ---------- textos fijos ----------
txt_1: .asciiz "Hola"
txt_2: .asciiz "1****"
txt_3: .asciiz "Hola a todos los que estʃ:a:ʅ haciendo un compilador nuevo¿:n"
txt_4: .asciiz "2****"
txt_5: .asciiz "22****22"
txt_6: .asciiz "3****"
txt_7: .asciiz "dddfffddd"
txt_8: .asciiz "4!!!!!!!!!!!!!!!!!"
txt_9: .asciiz "4****"
txt_10: .asciiz "5****"
txt_11: .asciiz "6****"
txt_12: .asciiz "Hola mundo"
txt_13: .asciiz "Mi string 1"

.text
.globl main
main:
    j void.principal                # inicio del programa: ir a principal

# ============================================================
# funcion float$func1
# ============================================================
float.func1:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -504
    la $t0, txt_1                   # string1 = "Hola"
    sw $t0, -8($fp)
    lw $t0, -8($fp)                 # sss = string1
    sw $t0, -4($fp)
    li $t0, 20                      # int2 = 20
    sw $t0, -12($fp)
    li $t0, 21                      # int3 = 21
    sw $t0, -16($fp)
    lw $t0, -12($fp)                # int4 = int2 + int3
    lw $t1, -16($fp)
    add $t2, $t0, $t1
    sw $t2, -20($fp)
    lw $t0, -20($fp)                # numero = int4
    sw $t0, var_numero
    li $t0, 97                      # char5 = 'a'
    sw $t0, -28($fp)
    lw $t0, -28($fp)                # letra = char5
    sw $t0, -24($fp)
    sw $zero, -32($fp)              # declaracion# float h
    lw $t0, var_numero              # int6 = numero
    sw $t0, -36($fp)
    lw $t0, -36($fp)                # int7 = int6 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -40($fp)
    lw $t0, -40($fp)                # numero = int7
    sw $t0, var_numero
    li $t0, 10                      # int8 = 10
    sw $t0, -44($fp)
    li $t0, 24                      # int9 = 24
    sw $t0, -48($fp)
    lw $t0, -44($fp)                # int10 = int8 + int9
    lw $t1, -48($fp)
    add $t2, $t0, $t1
    sw $t2, -52($fp)
    lw $t0, -36($fp)                # int11 = int6 * int10
    lw $t1, -52($fp)
    mul $t2, $t0, $t1
    sw $t2, -56($fp)
    li $t0, 44                      # int12 = 44
    sw $t0, -60($fp)
    li $t0, 88                      # int13 = 88
    sw $t0, -64($fp)
    lw $t0, -60($fp)                # int14 = int12 pot int13
    lw $t1, -64($fp)
    li $t2, 1

pot_ciclo_1:
    blez $t1, pot_fin_1
    mul $t2, $t2, $t0
    addi $t1, $t1, -1
    j pot_ciclo_1

pot_fin_1:
    sw $t2, -68($fp)
    lw $t0, -56($fp)                # int15 = int11 // int14
    lw $t1, -68($fp)
    div $t0, $t1
    mflo $t2
    sw $t2, -72($fp)
    lw $t0, var_numero              # int16 = numero
    sw $t0, -76($fp)
    lw $t0, -76($fp)                # int17 = int16 - 1
    li $t1, 1
    sub $t2, $t0, $t1
    sw $t2, -80($fp)
    lw $t0, -80($fp)                # numero = int17
    sw $t0, var_numero
    lw $t0, -72($fp)                # int18 = int15 mod int16
    lw $t1, -76($fp)
    div $t0, $t1
    mfhi $t2
    sw $t2, -84($fp)
    lw $t0, -84($fp)                # g = int18
    sw $t0, var_g
    l.s $f0, num_1                  # float19 = 4.4
    s.s $f0, -88($fp)
    lw $t0, -24($fp)                # char20 = letra
    sw $t0, -92($fp)
    li $t0, 98                      # char21 = 'b'
    sw $t0, -96($fp)
    lw $t0, -92($fp)                # param char20
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -96($fp)                # param char21
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal float.func1                 # float22 = call float$func1, 2
    addiu $sp, $sp, 8
    s.s $f0, -100($fp)
    l.s $f0, -88($fp)               # float23 = float19 * float22
    l.s $f2, -100($fp)
    mul.s $f4, $f0, $f2
    s.s $f4, -104($fp)
    l.s $f0, num_2                  # float24 = 9.9
    s.s $f0, -108($fp)
    l.s $f0, num_3                  # float25 = 6.7
    s.s $f0, -112($fp)
    l.s $f0, -108($fp)              # float26 = float24 - float25
    l.s $f2, -112($fp)
    sub.s $f4, $f0, $f2
    s.s $f4, -116($fp)
    l.s $f0, -104($fp)              # float27 = float23 / float26
    l.s $f2, -116($fp)
    div.s $f4, $f0, $f2
    s.s $f4, -120($fp)
    l.s $f0, -120($fp)              # h = float27
    s.s $f0, -32($fp)
    lw $t0, var_numero              # int28 = numero
    sw $t0, -124($fp)
    lw $a0, -124($fp)               # write int28
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    l.s $f0, num_4                  # float29 = -0.01
    s.s $f0, -128($fp)
    l.s $f0, -128($fp)              # ff = float29
    s.s $f0, var_ff
    li $t0, 33                      # char30 = '!'
    sw $t0, -136($fp)
    lw $t0, -136($fp)               # _miChar_ = char30
    sw $t0, -132($fp)
    li $t0, -1                      # int31 = -1
    sw $t0, -144($fp)
    lw $t0, -144($fp)               # _x30_ = int31
    sw $t0, -140($fp)
    li $t0, 0                       # bool32 = false
    sw $t0, -152($fp)
    lw $t0, -152($fp)               # _x40_ = bool32
    sw $t0, -148($fp)
    li $t0, 4                       # int33 = 4
    sw $t0, -164($fp)
    li $t0, 5                       # int34 = 5
    sw $t0, -168($fp)
    lw $t0, -164($fp)               # _x50_[0] = int33
    addiu $t9, $fp, -160
    sw $t0, 0($t9)
    lw $t0, -168($fp)               # _x50_[1] = int34
    addiu $t9, $fp, -160
    addiu $t9, $t9, 4
    sw $t0, 0($t9)
    li $t0, 1                       # int35 = 1
    sw $t0, -172($fp)
    li $t0, 0                       # int36 = 0
    sw $t0, -176($fp)
    lw $t0, -172($fp)               # int37 = int35 + int36
    lw $t1, -176($fp)
    add $t2, $t0, $t1
    sw $t2, -180($fp)
    li $t0, -9999                   # int38 = -9999
    sw $t0, -184($fp)
    lw $t0, -184($fp)               # _x50_[int37] = int38
    addiu $t9, $fp, -160
    lw $t8, -180($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    sw $t0, 0($t9)
    l.s $f0, num_5                  # float39 = 5.5
    s.s $f0, -204($fp)
    l.s $f0, num_6                  # float40 = 6.4
    s.s $f0, -208($fp)
    l.s $f0, num_7                  # float41 = 7.7
    s.s $f0, -212($fp)
    l.s $f0, num_8                  # float42 = 8.8
    s.s $f0, -216($fp)
    l.s $f0, -204($fp)              # _x502_[0] = float39
    addiu $t9, $fp, -200
    s.s $f0, 0($t9)
    l.s $f0, -208($fp)              # _x502_[1] = float40
    addiu $t9, $fp, -200
    addiu $t9, $t9, 4
    s.s $f0, 0($t9)
    l.s $f0, -212($fp)              # _x502_[2] = float41
    addiu $t9, $fp, -200
    addiu $t9, $t9, 8
    s.s $f0, 0($t9)
    l.s $f0, -216($fp)              # _x502_[3] = float42
    addiu $t9, $fp, -200
    addiu $t9, $t9, 12
    s.s $f0, 0($t9)
    la $t0, txt_2                   # string43 = "1****"
    sw $t0, -220($fp)
    lw $a0, -220($fp)               # write string43
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -140($fp)               # int44 = _x30_
    sw $t0, -224($fp)
    li $t0, 2                       # int45 = 2
    sw $t0, -228($fp)
    lw $t0, -224($fp)               # int46 = int44 - int45
    lw $t1, -228($fp)
    sub $t2, $t0, $t1
    sw $t2, -232($fp)
    addiu $t9, $fp, -200            # float47 = _x502_[int46]
    lw $t8, -232($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    l.s $f0, 0($t9)
    s.s $f0, -236($fp)
    lw $t0, -24($fp)                # char48 = letra
    sw $t0, -240($fp)
    li $t0, 99                      # char49 = 'c'
    sw $t0, -244($fp)
    lw $t0, -240($fp)               # param char48
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -244($fp)               # param char49
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal float.func1                 # float50 = call float$func1, 2
    addiu $sp, $sp, 8
    s.s $f0, -248($fp)
    l.s $f0, -236($fp)              # float51 = float47 * float50
    l.s $f2, -248($fp)
    mul.s $f4, $f0, $f2
    s.s $f4, -252($fp)
    l.s $f0, -252($fp)              # ff = float51
    s.s $f0, var_ff
    la $t0, txt_3                   # string52 = "Hola a todos los que estʃ:a:ʅ haciendo un compilador nuevo¿:n"
    sw $t0, -260($fp)
    lw $t0, -260($fp)               # _x503_ = string52
    sw $t0, -256($fp)
    lw $t0, -256($fp)               # string53 = _x503_
    sw $t0, -264($fp)
    lw $a0, -264($fp)               # write string53
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, var_numero              # int54 = numero
    sw $t0, -268($fp)
    li $t0, 45                      # int55 = 45
    sw $t0, -272($fp)
    lw $t0, -268($fp)               # bool56 = int54 <= int55
    lw $t1, -272($fp)
    sle $t2, $t0, $t1
    sw $t2, -276($fp)
    l.s $f0, var_ff                 # float57 = ff
    s.s $f0, -280($fp)
    l.s $f0, num_9                  # float58 = 5.6
    s.s $f0, -284($fp)
    l.s $f0, -280($fp)              # bool59 = float57 > float58
    l.s $f2, -284($fp)
    c.lt.s $f2, $f0
    li $t2, 1
    bc1t cmp_listo_2
    li $t2, 0

cmp_listo_2:
    sw $t2, -288($fp)
    lw $t0, -276($fp)               # bool60 = bool56 and bool59
    lw $t1, -288($fp)
    and $t2, $t0, $t1
    sw $t2, -292($fp)
    lw $t0, -292($fp)               # if bool60 goto func1$if$1$bloque1
    bnez $t0, func1.if.1.bloque1
    j func1.if.1.siguiente1         # goto func1$if$1$siguiente1

func1.if.1.bloque1:
    sw $zero, -296($fp)             # declaracion# int y1
    la $t0, txt_4                   # string61 = "2****"
    sw $t0, -300($fp)
    lw $a0, -300($fp)               # write string61
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    l.s $f0, var_ff                 # float62 = ff
    s.s $f0, -304($fp)
    l.s $f0, num_9                  # float63 = 5.6
    s.s $f0, -308($fp)
    l.s $f0, -304($fp)              # bool64 = float62 > float63
    l.s $f2, -308($fp)
    c.lt.s $f2, $f0
    li $t2, 1
    bc1t cmp_listo_3
    li $t2, 0

cmp_listo_3:
    sw $t2, -312($fp)
    lw $t0, var_numero              # int65 = numero
    sw $t0, -316($fp)
    li $t0, 45                      # int66 = 45
    sw $t0, -320($fp)
    lw $t0, -316($fp)               # bool67 = int65 > int66
    lw $t1, -320($fp)
    sgt $t2, $t0, $t1
    sw $t2, -324($fp)
    lw $t0, -312($fp)               # bool68 = bool64 or bool67
    lw $t1, -324($fp)
    or $t2, $t0, $t1
    sw $t2, -328($fp)
    lw $t0, -328($fp)               # bool69 = not bool68
    xori $t0, $t0, 1
    sw $t0, -332($fp)
    lw $t0, -332($fp)               # if bool69 goto func1$if$2$bloque1
    bnez $t0, func1.if.2.bloque1
    j func1.if.2.siguiente1         # goto func1$if$2$siguiente1

func1.if.2.bloque1:
    sw $zero, -336($fp)             # declaracion# int y
    li $t0, 97                      # char70 = 'a'
    sw $t0, -344($fp)
    lw $t0, -344($fp)               # ch33 = char70
    sw $t0, -340($fp)
    la $t0, txt_5                   # string71 = "22****22"
    sw $t0, -348($fp)
    lw $a0, -348($fp)               # write string71
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

func1.if.2.siguiente1:

func1.if.2.final:
    la $t0, txt_4                   # string72 = "2****"
    sw $t0, -352($fp)
    lw $a0, -352($fp)               # write string72
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 97                      # char73 = 'a'
    sw $t0, -356($fp)
    lw $t0, -356($fp)               # ch33 = char73
    sw $t0, -340($fp)
    j func1.if.1.final              # goto func1$if$1$final

func1.if.1.siguiente1:
    l.s $f0, num_10                 # float74 = 44.55
    s.s $f0, -360($fp)
    l.s $f0, var_ff                 # float75 = ff
    s.s $f0, -364($fp)
    l.s $f0, -360($fp)              # bool76 = float74 < float75
    l.s $f2, -364($fp)
    c.lt.s $f0, $f2
    li $t2, 1
    bc1t cmp_listo_4
    li $t2, 0

cmp_listo_4:
    sw $t2, -368($fp)
    lw $t0, -368($fp)               # if bool76 goto func1$if$1$bloque2
    bnez $t0, func1.if.1.bloque2
    j func1.if.1.siguiente2         # goto func1$if$1$siguiente2

func1.if.1.bloque2:
    la $t0, txt_6                   # string77 = "3****"
    sw $t0, -372($fp)
    lw $a0, -372($fp)               # write string77
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 45                      # int78 = 45
    sw $t0, -380($fp)
    lw $t0, -380($fp)               # y2 = int78
    sw $t0, -376($fp)
    la $t0, txt_7                   # string79 = "dddfffddd"
    sw $t0, -388($fp)
    lw $t0, -388($fp)               # str2 = string79
    sw $t0, -384($fp)

func1.while.3.condicion:
    li $t0, 0                       # bool80 = false
    sw $t0, -392($fp)
    lw $t0, -148($fp)               # bool81 = _x40_
    sw $t0, -396($fp)
    lw $t0, -392($fp)               # bool82 = bool80 and bool81
    lw $t1, -396($fp)
    and $t2, $t0, $t1
    sw $t2, -400($fp)
    lw $t0, -400($fp)               # if bool82 goto func1$while$3$bloque
    bnez $t0, func1.while.3.bloque
    j func1.while.3.final           # goto func1$while$3$final

func1.while.3.bloque:
    lw $t0, -256($fp)               # string83 = _x503_
    sw $t0, -404($fp)
    lw $a0, -404($fp)               # write string83
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    la $t0, txt_8                   # string84 = "4!!!!!!!!!!!!!!!!!"
    sw $t0, -408($fp)
    lw $a0, -408($fp)               # write string84
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

func1.for.4.inicializacion:
    li $t0, 0                       # int85 = 0
    sw $t0, -416($fp)
    lw $t0, -416($fp)               # numSS = int85
    sw $t0, -412($fp)

func1.for.4.condicion:
    lw $t0, -412($fp)               # int86 = numSS
    sw $t0, -420($fp)
    li $t0, 7                       # int87 = 7
    sw $t0, -424($fp)
    lw $t0, -420($fp)               # bool88 = int86 < int87
    lw $t1, -424($fp)
    slt $t2, $t0, $t1
    sw $t2, -428($fp)
    lw $t0, -428($fp)               # if bool88 goto func1$for$4$bloque
    bnez $t0, func1.for.4.bloque
    j func1.for.4.final             # goto func1$for$4$final

func1.for.4.incremento:
    lw $t0, -412($fp)               # int89 = numSS
    sw $t0, -432($fp)
    lw $t0, -432($fp)               # int90 = int89 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -436($fp)
    lw $t0, -436($fp)               # numSS = int90
    sw $t0, -412($fp)
    j func1.for.4.condicion         # goto func1$for$4$condicion

func1.for.4.bloque:
    lw $t0, -412($fp)               # int91 = numSS
    sw $t0, -440($fp)
    lw $a0, -440($fp)               # write int91
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j func1.for.4.incremento        # goto func1$for$4$incremento

func1.for.4.final:
    la $t0, txt_8                   # string92 = "4!!!!!!!!!!!!!!!!!"
    sw $t0, -444($fp)
    lw $a0, -444($fp)               # write string92
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j func1.while.3.condicion       # goto func1$while$3$condicion

func1.while.3.final:
    la $t0, txt_6                   # string93 = "3****"
    sw $t0, -448($fp)
    lw $a0, -448($fp)               # write string93
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j func1.if.1.final              # goto func1$if$1$final

func1.if.1.siguiente2:
    la $t0, txt_9                   # string94 = "4****"
    sw $t0, -452($fp)
    lw $a0, -452($fp)               # write string94
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

func1.if.1.final:
    l.s $f0, num_11                 # float95 = 444.555
    s.s $f0, -456($fp)
    l.s $f0, var_ff                 # float96 = ff
    s.s $f0, -460($fp)
    l.s $f0, -456($fp)              # bool97 = float95 < float96
    l.s $f2, -460($fp)
    c.lt.s $f0, $f2
    li $t2, 1
    bc1t cmp_listo_5
    li $t2, 0

cmp_listo_5:
    sw $t2, -464($fp)
    lw $t0, -464($fp)               # if bool97 goto func1$if$5$bloque1
    bnez $t0, func1.if.5.bloque1
    j func1.if.5.siguiente1         # goto func1$if$5$siguiente1

func1.if.5.bloque1:
    la $t0, txt_10                  # string98 = "5****"
    sw $t0, -468($fp)
    lw $a0, -468($fp)               # write string98
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    j func1.if.5.final              # goto func1$if$5$final

func1.if.5.siguiente1:
    la $t0, txt_11                  # string99 = "6****"
    sw $t0, -472($fp)
    lw $a0, -472($fp)               # write string99
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

func1.if.5.final:
    la $t0, txt_12                  # string100 = "Hola mundo"
    sw $t0, -476($fp)
    lw $a0, -476($fp)               # write string100
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    l.s $f0, num_12                 # float101 = -5.6
    s.s $f0, -480($fp)
    l.s $f0, var_ff                 # float102 = ff
    s.s $f0, -484($fp)
    l.s $f0, -480($fp)              # float103 = float101 / float102
    l.s $f2, -484($fp)
    div.s $f4, $f0, $f2
    s.s $f4, -488($fp)
    lw $t0, -24($fp)                # char104 = letra
    sw $t0, -492($fp)
    li $t0, 100                     # char105 = 'd'
    sw $t0, -496($fp)
    lw $t0, -492($fp)               # param char104
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -496($fp)               # param char105
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal float.func1                 # float106 = call float$func1, 2
    addiu $sp, $sp, 8
    s.s $f0, -500($fp)
    l.s $f0, -488($fp)              # float107 = float103 * float106
    l.s $f2, -500($fp)
    mul.s $f4, $f0, $f2
    s.s $f4, -504($fp)
    l.s $f0, -504($fp)              # return float107
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # float$func1$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion int$_func2_
# ============================================================
int._func2_:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -8
    sw $zero, -4($fp)               # declaracion# int j
    lw $t0, 8($fp)                  # int108 = _i1_
    sw $t0, -8($fp)
    lw $v0, -8($fp)                 # return int108
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # int$_func2_$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion bool$_func3_
# ============================================================
bool._func3_:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -8
    sw $zero, -4($fp)               # declaracion# string _b1_
    li $t0, 1                       # bool109 = true
    sw $t0, -8($fp)
    lw $v0, -8($fp)                 # return bool109
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # bool$_func3_$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion void$principal
# ============================================================
void.principal:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -116
    li $t0, 33                      # char110 = '!'
    sw $t0, -8($fp)
    lw $t0, -8($fp)                 # miChar = char110
    sw $t0, -4($fp)
    la $t0, txt_13                  # string111 = "Mi string 1"
    sw $t0, -16($fp)
    lw $t0, -16($fp)                # str1 = string111
    sw $t0, -12($fp)
    l.s $f0, num_13                 # float112 = 56.6
    s.s $f0, -24($fp)
    l.s $f0, -24($fp)               # fl1 = float112
    s.s $f0, -20($fp)
    li $t0, 10                      # int113 = 10
    sw $t0, -32($fp)
    lw $t0, -32($fp)                # in1 = int113
    sw $t0, -28($fp)
    l.s $f0, num_3                  # float114 = 6.7
    s.s $f0, -40($fp)
    l.s $f0, num_14                 # float115 = 8.9
    s.s $f0, -44($fp)
    l.s $f0, -40($fp)               # bool116 = float114 != float115
    l.s $f2, -44($fp)
    c.eq.s $f0, $f2
    li $t2, 1
    bc1f cmp_listo_6
    li $t2, 0

cmp_listo_6:
    sw $t2, -48($fp)
    lw $t0, -48($fp)                # bl0 = bool116
    sw $t0, -36($fp)
    li $t0, 4                       # int117 = 4
    sw $t0, -60($fp)
    li $t0, 5                       # int118 = 5
    sw $t0, -64($fp)
    lw $t0, -60($fp)                # _x50_[0] = int117
    addiu $t9, $fp, -56
    sw $t0, 0($t9)
    lw $t0, -64($fp)                # _x50_[1] = int118
    addiu $t9, $fp, -56
    addiu $t9, $t9, 4
    sw $t0, 0($t9)
    li $t0, 0                       # int119 = 0
    sw $t0, -68($fp)
    lw $t0, -4($fp)                 # char120 = miChar
    sw $t0, -72($fp)
    li $t0, 101                     # char121 = 'e'
    sw $t0, -76($fp)
    lw $t0, -72($fp)                # param char120
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -76($fp)                # param char121
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal float.func1                 # float122 = call float$func1, 2
    addiu $sp, $sp, 8
    s.s $f0, -80($fp)
    l.s $f0, num_5                  # float123 = 5.5
    s.s $f0, -84($fp)
    l.s $f0, -80($fp)               # float124 = float122 * float123
    l.s $f2, -84($fp)
    mul.s $f4, $f0, $f2
    s.s $f4, -88($fp)
    lw $t0, -28($fp)                # int125 = in1
    sw $t0, -92($fp)
    addiu $t9, $fp, -56             # int126 = _x50_[0]
    lw $t0, 0($t9)
    sw $t0, -96($fp)
    lw $t0, -92($fp)                # int127 = int125 // int126
    lw $t1, -96($fp)
    div $t0, $t1
    mflo $t2
    sw $t2, -100($fp)
    l.s $f0, -88($fp)               # param float124
    addiu $sp, $sp, -4
    s.s $f0, 0($sp)
    lw $t0, -100($fp)               # param int127
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int._func2_                 # int128 = call int$_func2_, 2
    addiu $sp, $sp, 8
    sw $v0, -104($fp)
    lw $t0, -68($fp)                # int129 = int119 + int128
    lw $t1, -104($fp)
    add $t2, $t0, $t1
    sw $t2, -108($fp)
    li $t0, 0                       # int130 = 0
    sw $t0, -112($fp)
    lw $t0, -108($fp)               # int131 = int129 + int130
    lw $t1, -112($fp)
    add $t2, $t0, $t1
    sw $t2, -116($fp)
    lw $t0, -116($fp)               # in1 = int131
    sw $t0, -28($fp)
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
