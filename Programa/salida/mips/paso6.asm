.data

# ---------- variables del programa ----------
.align 2
var_numeros: .space 20
.align 2
var_notas: .space 12
.align 2
var_copia: .space 20

# ---------- temporales del codigo intermedio ----------
tmp_int1: .word 0
tmp_int2: .word 0
tmp_int3: .word 0
tmp_int4: .word 0
tmp_int5: .word 0
tmp_float6: .float 0.0
tmp_float7: .float 0.0
tmp_float8: .float 0.0

# ---------- numeros float fijos ----------
num_1: .float 9.5
num_2: .float 8.0
num_3: .float 7.25

.text
.globl main
main:
    li $t0, 10                      # int1 = 10
    sw $t0, tmp_int1
    li $t0, 20                      # int2 = 20
    sw $t0, tmp_int2
    li $t0, 30                      # int3 = 30
    sw $t0, tmp_int3
    li $t0, 40                      # int4 = 40
    sw $t0, tmp_int4
    li $t0, 50                      # int5 = 50
    sw $t0, tmp_int5
    lw $t0, tmp_int1                # numeros[0] = int1
    la $t9, var_numeros
    sw $t0, 0($t9)
    lw $t0, tmp_int2                # numeros[1] = int2
    la $t9, var_numeros
    addiu $t9, $t9, 4
    sw $t0, 0($t9)
    lw $t0, tmp_int3                # numeros[2] = int3
    la $t9, var_numeros
    addiu $t9, $t9, 8
    sw $t0, 0($t9)
    lw $t0, tmp_int4                # numeros[3] = int4
    la $t9, var_numeros
    addiu $t9, $t9, 12
    sw $t0, 0($t9)
    lw $t0, tmp_int5                # numeros[4] = int5
    la $t9, var_numeros
    addiu $t9, $t9, 16
    sw $t0, 0($t9)
    l.s $f0, num_1                  # float6 = 9.5
    s.s $f0, tmp_float6
    l.s $f0, num_2                  # float7 = 8.0
    s.s $f0, tmp_float7
    l.s $f0, num_3                  # float8 = 7.25
    s.s $f0, tmp_float8
    l.s $f0, tmp_float6             # notas[0] = float6
    la $t9, var_notas
    s.s $f0, 0($t9)
    l.s $f0, tmp_float7             # notas[1] = float7
    la $t9, var_notas
    addiu $t9, $t9, 4
    s.s $f0, 0($t9)
    l.s $f0, tmp_float8             # notas[2] = float8
    la $t9, var_notas
    addiu $t9, $t9, 8
    s.s $f0, 0($t9)
    j void.principal                # inicio del programa: ir a principal

# ============================================================
# funcion int$sumarHasta
# ============================================================
int.sumarHasta:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -84
    li $t0, 1                       # int9 = 1
    sw $t0, -20($fp)
    li $t0, 2                       # int10 = 2
    sw $t0, -24($fp)
    li $t0, 3                       # int11 = 3
    sw $t0, -28($fp)
    li $t0, 4                       # int12 = 4
    sw $t0, -32($fp)
    lw $t0, -20($fp)                # datos[0] = int9
    addiu $t9, $fp, -16
    sw $t0, 0($t9)
    lw $t0, -24($fp)                # datos[1] = int10
    addiu $t9, $fp, -16
    addiu $t9, $t9, 4
    sw $t0, 0($t9)
    lw $t0, -28($fp)                # datos[2] = int11
    addiu $t9, $fp, -16
    addiu $t9, $t9, 8
    sw $t0, 0($t9)
    lw $t0, -32($fp)                # datos[3] = int12
    addiu $t9, $fp, -16
    addiu $t9, $t9, 12
    sw $t0, 0($t9)
    li $t0, 0                       # int13 = 0
    sw $t0, -40($fp)
    lw $t0, -40($fp)                # s = int13
    sw $t0, -36($fp)

sumarHasta.for.1.inicializacion:
    li $t0, 0                       # int14 = 0
    sw $t0, -48($fp)
    lw $t0, -48($fp)                # i = int14
    sw $t0, -44($fp)

sumarHasta.for.1.condicion:
    lw $t0, -44($fp)                # int15 = i
    sw $t0, -52($fp)
    lw $t0, 8($fp)                  # int16 = tope
    sw $t0, -56($fp)
    lw $t0, -52($fp)                # bool17 = int15 < int16
    lw $t1, -56($fp)
    slt $t2, $t0, $t1
    sw $t2, -60($fp)
    lw $t0, -60($fp)                # if bool17 goto sumarHasta$for$1$bloque
    bnez $t0, sumarHasta.for.1.bloque
    j sumarHasta.for.1.final        # goto sumarHasta$for$1$final

sumarHasta.for.1.incremento:
    lw $t0, -44($fp)                # int18 = i
    sw $t0, -64($fp)
    lw $t0, -64($fp)                # int19 = int18 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -68($fp)
    lw $t0, -68($fp)                # i = int19
    sw $t0, -44($fp)
    j sumarHasta.for.1.condicion    # goto sumarHasta$for$1$condicion

sumarHasta.for.1.bloque:
    lw $t0, -36($fp)                # int20 = s
    sw $t0, -72($fp)
    addiu $t9, $fp, -16             # int21 = datos[i]
    lw $t8, -44($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    lw $t0, 0($t9)
    sw $t0, -76($fp)
    lw $t0, -72($fp)                # int22 = int20 + int21
    lw $t1, -76($fp)
    add $t2, $t0, $t1
    sw $t2, -80($fp)
    lw $t0, -80($fp)                # s = int22
    sw $t0, -36($fp)
    j sumarHasta.for.1.incremento   # goto sumarHasta$for$1$incremento

sumarHasta.for.1.final:
    lw $t0, -36($fp)                # int23 = s
    sw $t0, -84($fp)
    lw $v0, -84($fp)                # return int23
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # int$sumarHasta$final
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra

# ============================================================
# funcion int$doble
# ============================================================
int.doble:
    addiu $sp, $sp, -8              # guardar $ra y $fp, y reservar las variables locales
    sw $ra, 4($sp)
    sw $fp, 0($sp)
    move $fp, $sp
    addiu $sp, $sp, -12
    lw $t0, 8($fp)                  # int24 = x
    sw $t0, -4($fp)
    li $t0, 2                       # int25 = 2
    sw $t0, -8($fp)
    lw $t0, -4($fp)                 # int26 = int24 * int25
    lw $t1, -8($fp)
    mul $t2, $t0, $t1
    sw $t2, -12($fp)
    lw $v0, -12($fp)                # return int26
    move $sp, $fp
    lw $fp, 0($sp)
    lw $ra, 4($sp)
    addiu $sp, $sp, 8
    jr $ra
    move $sp, $fp                   # int$doble$final
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
    addiu $sp, $sp, -128
    la $t9, var_numeros             # int27 = numeros[0]
    lw $t0, 0($t9)
    sw $t0, -4($fp)
    lw $a0, -4($fp)                 # write int27
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    la $t9, var_numeros             # int28 = numeros[4]
    addiu $t9, $t9, 16
    lw $t0, 0($t9)
    sw $t0, -8($fp)
    lw $a0, -8($fp)                 # write int28
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 99                      # int29 = 99
    sw $t0, -12($fp)
    lw $t0, -12($fp)                # numeros[2] = int29
    la $t9, var_numeros
    addiu $t9, $t9, 8
    sw $t0, 0($t9)
    la $t9, var_numeros             # int30 = numeros[2]
    addiu $t9, $t9, 8
    lw $t0, 0($t9)
    sw $t0, -16($fp)
    lw $a0, -16($fp)                # write int30
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 1                       # int31 = 1
    sw $t0, -24($fp)
    lw $t0, -24($fp)                # k = int31
    sw $t0, -20($fp)
    la $t9, var_numeros             # int32 = numeros[k]
    lw $t8, -20($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    lw $t0, 0($t9)
    sw $t0, -28($fp)
    lw $a0, -28($fp)                # write int32
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    lw $t0, -20($fp)                # int33 = k
    sw $t0, -32($fp)
    li $t0, 2                       # int34 = 2
    sw $t0, -36($fp)
    lw $t0, -32($fp)                # int35 = int33 + int34
    lw $t1, -36($fp)
    add $t2, $t0, $t1
    sw $t2, -40($fp)
    la $t9, var_numeros             # int36 = numeros[k]
    lw $t8, -20($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    lw $t0, 0($t9)
    sw $t0, -44($fp)
    li $t0, 5                       # int37 = 5
    sw $t0, -48($fp)
    lw $t0, -44($fp)                # int38 = int36 + int37
    lw $t1, -48($fp)
    add $t2, $t0, $t1
    sw $t2, -52($fp)
    lw $t0, -52($fp)                # copia[int35] = int38
    la $t9, var_copia
    lw $t8, -40($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    sw $t0, 0($t9)
    la $t9, var_copia               # int39 = copia[3]
    addiu $t9, $t9, 12
    lw $t0, 0($t9)
    sw $t0, -56($fp)
    lw $a0, -56($fp)                # write int39
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 0                       # int40 = 0
    sw $t0, -64($fp)
    lw $t0, -64($fp)                # total = int40
    sw $t0, -60($fp)

principal.for.2.inicializacion:
    li $t0, 0                       # int41 = 0
    sw $t0, -72($fp)
    lw $t0, -72($fp)                # i = int41
    sw $t0, -68($fp)

principal.for.2.condicion:
    lw $t0, -68($fp)                # int42 = i
    sw $t0, -76($fp)
    li $t0, 5                       # int43 = 5
    sw $t0, -80($fp)
    lw $t0, -76($fp)                # bool44 = int42 < int43
    lw $t1, -80($fp)
    slt $t2, $t0, $t1
    sw $t2, -84($fp)
    lw $t0, -84($fp)                # if bool44 goto principal$for$2$bloque
    bnez $t0, principal.for.2.bloque
    j principal.for.2.final         # goto principal$for$2$final

principal.for.2.incremento:
    lw $t0, -68($fp)                # int45 = i
    sw $t0, -88($fp)
    lw $t0, -88($fp)                # int46 = int45 + 1
    li $t1, 1
    add $t2, $t0, $t1
    sw $t2, -92($fp)
    lw $t0, -92($fp)                # i = int46
    sw $t0, -68($fp)
    j principal.for.2.condicion     # goto principal$for$2$condicion

principal.for.2.bloque:
    lw $t0, -60($fp)                # int47 = total
    sw $t0, -96($fp)
    la $t9, var_numeros             # int48 = numeros[i]
    lw $t8, -68($fp)
    sll $t8, $t8, 2
    addu $t9, $t9, $t8
    lw $t0, 0($t9)
    sw $t0, -100($fp)
    lw $t0, -96($fp)                # int49 = int47 + int48
    lw $t1, -100($fp)
    add $t2, $t0, $t1
    sw $t2, -104($fp)
    lw $t0, -104($fp)               # total = int49
    sw $t0, -60($fp)
    j principal.for.2.incremento    # goto principal$for$2$incremento

principal.for.2.final:
    lw $t0, -60($fp)                # int50 = total
    sw $t0, -108($fp)
    lw $a0, -108($fp)               # write int50
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    la $t9, var_notas               # float51 = notas[2]
    addiu $t9, $t9, 8
    l.s $f0, 0($t9)
    s.s $f0, -112($fp)
    l.s $f12, -112($fp)             # write float51
    li $v0, 2
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $t0, 4                       # int52 = 4
    sw $t0, -116($fp)
    lw $t0, -116($fp)               # param int52
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int.sumarHasta              # int53 = call int$sumarHasta, 1
    addiu $sp, $sp, 4
    sw $v0, -120($fp)
    lw $a0, -120($fp)               # write int53
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    la $t9, var_numeros             # int54 = numeros[4]
    addiu $t9, $t9, 16
    lw $t0, 0($t9)
    sw $t0, -124($fp)
    lw $t0, -124($fp)               # param int54
    addiu $sp, $sp, -4
    sw $t0, 0($sp)
    jal int.doble                   # int55 = call int$doble, 1
    addiu $sp, $sp, 4
    sw $v0, -128($fp)
    lw $a0, -128($fp)               # write int55
    li $v0, 1
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    li $v0, 10                      # return
    syscall
    li $v0, 10                      # void$principal$final
    syscall
