.data

# ---------- variables del programa ----------
var_contador: .word 0
var_pi: .float 0.0

# ---------- temporales del codigo intermedio ----------
tmp_int1: .word 0
tmp_float2: .float 0.0

# ---------- numeros float fijos ----------
num_1: .float 3.5
num_2: .float 2.0

# ---------- textos fijos ----------
txt_1: .asciiz "inicial correcta"
txt_2: .asciiz "mayuscula correcta"

.text
.globl main
main:
    li $t0, 0                       # int1 = 0
    sw $t0, tmp_int1
    lw $t0, tmp_int1                # contador = int1
    sw $t0, var_contador
    l.s $f0, num_1                  # float2 = 3.5
    s.s $f0, tmp_float2
    l.s $f0, tmp_float2             # pi = float2
    s.s $f0, var_pi
    j void.principal                # inicio del programa: ir a principal

# ============================================================
# funcion int$factorial
# ============================================================
int.factorial:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -40
    lw $t0, 8($fp)                  # int3 = n
    sw $t0, -4($fp)
    li $t0, 1                       # int4 = 1
    sw $t0, -8($fp)
    lw $t0, -4($fp)                 # bool5 = int3 <= int4
    lw $t1, -8($fp)
    sle $t2, $t0, $t1
    sw $t2, -12($fp)
    lw $t0, -12($fp)                # if bool5 goto factorial$if$1$bloque1
    bnez $t0, factorial.if.1.bloque1
    j factorial.if.1.siguiente1     # goto factorial$if$1$siguiente1

factorial.if.1.bloque1:
    li $t0, 1                       # int6 = 1
    sw $t0, -16($fp)
    lw $v0, -16($fp)                # return int6
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

factorial.if.1.siguiente1:

factorial.if.1.final:
    lw $t0, 8($fp)                  # int7 = n
    sw $t0, -20($fp)
    lw $t0, 8($fp)                  # int8 = n
    sw $t0, -24($fp)
    li $t0, 1                       # int9 = 1
    sw $t0, -28($fp)
    lw $t0, -24($fp)                # int10 = int8 - int9
    lw $t1, -28($fp)
    sub $t2, $t0, $t1
    sw $t2, -32($fp)
    lw $t0, -32($fp)                # param int10
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int.factorial               # int11 = call int$factorial, 1
    addiu $sp, $sp, 4
    sw $v0, -36($fp)
    lw $t0, -20($fp)                # int12 = int7 * int11
    lw $t1, -36($fp)
    mul $t2, $t0, $t1
    sw $t2, -40($fp)
    lw $v0, -40($fp)                # return int12
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # int$factorial$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion int$fib
# ============================================================
int.fib:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -52
    lw $t0, 8($fp)                  # int13 = n
    sw $t0, -4($fp)
    li $t0, 2                       # int14 = 2
    sw $t0, -8($fp)
    lw $t0, -4($fp)                 # bool15 = int13 < int14
    lw $t1, -8($fp)
    slt $t2, $t0, $t1
    sw $t2, -12($fp)
    lw $t0, -12($fp)                # if bool15 goto fib$if$2$bloque1
    bnez $t0, fib.if.2.bloque1
    j fib.if.2.siguiente1           # goto fib$if$2$siguiente1

fib.if.2.bloque1:
    lw $t0, 8($fp)                  # int16 = n
    sw $t0, -16($fp)
    lw $v0, -16($fp)                # return int16
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

fib.if.2.siguiente1:

fib.if.2.final:
    lw $t0, 8($fp)                  # int17 = n
    sw $t0, -20($fp)
    li $t0, 1                       # int18 = 1
    sw $t0, -24($fp)
    lw $t0, -20($fp)                # int19 = int17 - int18
    lw $t1, -24($fp)
    sub $t2, $t0, $t1
    sw $t2, -28($fp)
    lw $t0, -28($fp)                # param int19
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int.fib                     # int20 = call int$fib, 1
    addiu $sp, $sp, 4
    sw $v0, -32($fp)
    lw $t0, 8($fp)                  # int21 = n
    sw $t0, -36($fp)
    li $t0, 2                       # int22 = 2
    sw $t0, -40($fp)
    lw $t0, -36($fp)                # int23 = int21 - int22
    lw $t1, -40($fp)
    sub $t2, $t0, $t1
    sw $t2, -44($fp)
    lw $t0, -44($fp)                # param int23
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int.fib                     # int24 = call int$fib, 1
    addiu $sp, $sp, 4
    sw $v0, -48($fp)
    lw $t0, -32($fp)                # int25 = int20 + int24
    lw $t1, -48($fp)
    add $t2, $t0, $t1
    sw $t2, -52($fp)
    lw $v0, -52($fp)                # return int25
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # int$fib$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion int$suma
# ============================================================
int.suma:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -20
    lw $t0, 12($fp)                 # int26 = a
    sw $t0, -8($fp)
    lw $t0, 8($fp)                  # int27 = b
    sw $t0, -12($fp)
    lw $t0, -8($fp)                 # int28 = int26 + int27
    lw $t1, -12($fp)
    add $t2, $t0, $t1
    sw $t2, -16($fp)
    lw $t0, -16($fp)                # r = int28
    sw $t0, -4($fp)
    lw $t0, -4($fp)                 # int29 = r
    sw $t0, -20($fp)
    lw $v0, -20($fp)                # return int29
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # int$suma$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion float$area
# ============================================================
float.area:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -20
    l.s $f0, var_pi                 # float30 = pi
    s.s $f0, -4($fp)
    l.s $f0, 8($fp)                 # float31 = r
    s.s $f0, -8($fp)
    l.s $f0, -4($fp)                # float32 = float30 * float31
    l.s $f2, -8($fp)
    mul.s $f4, $f0, $f2
    s.s $f4, -12($fp)
    l.s $f0, 8($fp)                 # float33 = r
    s.s $f0, -16($fp)
    l.s $f0, -12($fp)               # float34 = float32 * float33
    l.s $f2, -16($fp)
    mul.s $f4, $f0, $f2
    s.s $f4, -20($fp)
    l.s $f0, -20($fp)               # return float34
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # float$area$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion char$inicial
# ============================================================
char.inicial:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -12
    lw $t0, 8($fp)                  # bool35 = mayus
    sw $t0, -4($fp)
    lw $t0, -4($fp)                 # if bool35 goto inicial$if$3$bloque1
    bnez $t0, inicial.if.3.bloque1
    j inicial.if.3.siguiente1       # goto inicial$if$3$siguiente1

inicial.if.3.bloque1:
    li $t0, 65                      # char36 = 'A'
    sw $t0, -8($fp)
    lw $v0, -8($fp)                 # return char36
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

inicial.if.3.siguiente1:

inicial.if.3.final:
    lw $t0, 12($fp)                 # char37 = c
    sw $t0, -12($fp)
    lw $v0, -12($fp)                # return char37
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # char$inicial$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion int$contar
# ============================================================
int.contar:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -16
    lw $t0, var_contador            # int38 = contador
    sw $t0, -4($fp)
    li $t0, 1                       # int39 = 1
    sw $t0, -8($fp)
    lw $t0, -4($fp)                 # int40 = int38 + int39
    lw $t1, -8($fp)
    add $t2, $t0, $t1
    sw $t2, -12($fp)
    lw $t0, -12($fp)                # contador = int40
    sw $t0, var_contador
    lw $t0, var_contador            # int41 = contador
    sw $t0, -16($fp)
    lw $v0, -16($fp)                # return int41
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # int$contar$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion int$diez
# ============================================================
int.diez:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -4
    li $t0, 10                      # int42 = 10
    sw $t0, -4($fp)
    lw $v0, -4($fp)                 # return int42
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # int$diez$final
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
    addiu $sp, $sp, -132
    li $t0, 5                       # int43 = 5
    sw $t0, -4($fp)
    lw $t0, -4($fp)                 # param int43
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int.factorial               # int44 = call int$factorial, 1
    addiu $sp, $sp, 4
    sw $v0, -8($fp)
    lw $a0, -8($fp)                 # write int44
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 10                      # int45 = 10
    sw $t0, -12($fp)
    lw $t0, -12($fp)                # param int45
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int.fib                     # int46 = call int$fib, 1
    addiu $sp, $sp, 4
    sw $v0, -16($fp)
    lw $a0, -16($fp)                # write int46
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 3                       # int47 = 3
    sw $t0, -20($fp)
    li $t0, 4                       # int48 = 4
    sw $t0, -24($fp)
    lw $t0, -20($fp)                # param int47
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -24($fp)                # param int48
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int.suma                    # int49 = call int$suma, 2
    addiu $sp, $sp, 8
    sw $v0, -28($fp)
    lw $a0, -28($fp)                # write int49
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 1                       # int50 = 1
    sw $t0, -32($fp)
    li $t0, 2                       # int51 = 2
    sw $t0, -36($fp)
    lw $t0, -32($fp)                # param int50
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -36($fp)                # param int51
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int.suma                    # int52 = call int$suma, 2
    addiu $sp, $sp, 8
    sw $v0, -40($fp)
    jal int.diez                    # int53 = call int$diez, 0
    sw $v0, -44($fp)
    lw $t0, -40($fp)                # param int52
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -44($fp)                # param int53
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int.suma                    # int54 = call int$suma, 2
    addiu $sp, $sp, 8
    sw $v0, -48($fp)
    lw $a0, -48($fp)                # write int54
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    l.s $f0, num_2                  # float55 = 2.0
    s.s $f0, -52($fp)
    l.s $f0, -52($fp)               # param float55
    addiu $sp, $sp, -4
    s.s $f0, 0($sp)
    jal float.area                  # float56 = call float$area, 1
    addiu $sp, $sp, 4
    s.s $f0, -56($fp)
    l.s $f12, -56($fp)              # write float56
    li $v0, 2
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 122                     # char57 = 'z'
    sw $t0, -64($fp)
    li $t0, 0                       # bool58 = false
    sw $t0, -68($fp)
    lw $t0, -64($fp)                # param char57
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -68($fp)                # param bool58
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal char.inicial                # char59 = call char$inicial, 2
    addiu $sp, $sp, 8
    sw $v0, -72($fp)
    lw $t0, -72($fp)                # letra = char59
    sw $t0, -60($fp)
    lw $t0, -60($fp)                # char60 = letra
    sw $t0, -76($fp)
    li $t0, 122                     # char61 = 'z'
    sw $t0, -80($fp)
    lw $t0, -76($fp)                # bool62 = char60 == char61
    lw $t1, -80($fp)
    seq $t2, $t0, $t1
    sw $t2, -84($fp)
    lw $t0, -84($fp)                # if bool62 goto principal$if$4$bloque1
    bnez $t0, principal.if.4.bloque1
    j principal.if.4.siguiente1     # goto principal$if$4$siguiente1

principal.if.4.bloque1:
    la $t0, txt_1                   # string63 = "inicial correcta"
    sw $t0, -88($fp)
    lw $a0, -88($fp)                # write string63
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.4.siguiente1:

principal.if.4.final:
    li $t0, 122                     # char64 = 'z'
    sw $t0, -92($fp)
    li $t0, 1                       # bool65 = true
    sw $t0, -96($fp)
    lw $t0, -92($fp)                # param char64
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    lw $t0, -96($fp)                # param bool65
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal char.inicial                # char66 = call char$inicial, 2
    addiu $sp, $sp, 8
    sw $v0, -100($fp)
    lw $t0, -100($fp)               # letra = char66
    sw $t0, -60($fp)
    lw $t0, -60($fp)                # char67 = letra
    sw $t0, -104($fp)
    li $t0, 65                      # char68 = 'A'
    sw $t0, -108($fp)
    lw $t0, -104($fp)               # bool69 = char67 == char68
    lw $t1, -108($fp)
    seq $t2, $t0, $t1
    sw $t2, -112($fp)
    lw $t0, -112($fp)               # if bool69 goto principal$if$5$bloque1
    bnez $t0, principal.if.5.bloque1
    j principal.if.5.siguiente1     # goto principal$if$5$siguiente1

principal.if.5.bloque1:
    la $t0, txt_2                   # string70 = "mayuscula correcta"
    sw $t0, -116($fp)
    lw $a0, -116($fp)               # write string70
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.5.siguiente1:

principal.if.5.final:
    jal int.contar                  # int71 = call int$contar, 0
    sw $v0, -120($fp)
    jal int.contar                  # int72 = call int$contar, 0
    sw $v0, -124($fp)
    jal int.contar                  # int73 = call int$contar, 0
    sw $v0, -128($fp)
    lw $t0, var_contador            # int74 = contador
    sw $t0, -132($fp)
    lw $a0, -132($fp)               # write int74
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
