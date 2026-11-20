.data

# ---------- variables del programa ----------
var_x: .float 0.0
var_y: .float 0.0
var_z: .float 0.0
var_saludo: .word 0
var_letra: .word 0

# ---------- temporales del codigo intermedio ----------
tmp_float1: .float 0.0
tmp_float2: .float 0.0
tmp_float3: .float 0.0
tmp_float4: .float 0.0
tmp_float5: .float 0.0
tmp_float6: .float 0.0
tmp_float7: .float 0.0
tmp_float8: .float 0.0
tmp_float9: .float 0.0
tmp_float10: .float 0.0
tmp_float11: .float 0.0
tmp_float12: .float 0.0
tmp_float13: .float 0.0
tmp_float14: .float 0.0
tmp_string15: .word 0
tmp_string16: .word 0
tmp_string17: .word 0
tmp_float18: .float 0.0
tmp_float19: .float 0.0
tmp_bool20: .word 0
tmp_string21: .word 0
tmp_char22: .word 0
tmp_char23: .word 0
tmp_char24: .word 0
tmp_bool25: .word 0
tmp_string26: .word 0

# ---------- numeros float fijos ----------
num_1: .float 7.5
num_2: .float 2.0

# ---------- textos fijos ----------
txt_1: .asciiz "Hola MIPS"
txt_2: .asciiz "fin de textos"
txt_3: .asciiz "x es mayor"
txt_4: .asciiz "la letra es A"

.text
.globl main
main:

void.principal:
    l.s $f0, num_1                  # float1 = 7.5
    s.s $f0, tmp_float1
    l.s $f0, tmp_float1             # x = float1
    s.s $f0, var_x
    l.s $f0, num_2                  # float2 = 2.0
    s.s $f0, tmp_float2
    l.s $f0, tmp_float2             # y = float2
    s.s $f0, var_y
    l.s $f0, var_x                  # float3 = x
    s.s $f0, tmp_float3
    l.s $f0, var_y                  # float4 = y
    s.s $f0, tmp_float4
    l.s $f0, tmp_float3             # float5 = float3 + float4
    l.s $f2, tmp_float4
    add.s $f4, $f0, $f2
    s.s $f4, tmp_float5
    l.s $f0, tmp_float5             # z = float5
    s.s $f0, var_z
    l.s $f0, var_z                  # float6 = z
    s.s $f0, tmp_float6
    l.s $f12, tmp_float6            # write float6
    li $v0, 2
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    l.s $f0, var_x                  # float7 = x
    s.s $f0, tmp_float7
    l.s $f0, var_y                  # float8 = y
    s.s $f0, tmp_float8
    l.s $f0, tmp_float7             # float9 = float7 * float8
    l.s $f2, tmp_float8
    mul.s $f4, $f0, $f2
    s.s $f4, tmp_float9
    l.s $f0, tmp_float9             # z = float9
    s.s $f0, var_z
    l.s $f0, var_z                  # float10 = z
    s.s $f0, tmp_float10
    l.s $f12, tmp_float10           # write float10
    li $v0, 2
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    l.s $f0, var_x                  # float11 = x
    s.s $f0, tmp_float11
    l.s $f0, var_y                  # float12 = y
    s.s $f0, tmp_float12
    l.s $f0, tmp_float11            # float13 = float11 / float12
    l.s $f2, tmp_float12
    div.s $f4, $f0, $f2
    s.s $f4, tmp_float13
    l.s $f0, tmp_float13            # z = float13
    s.s $f0, var_z
    l.s $f0, var_z                  # float14 = z
    s.s $f0, tmp_float14
    l.s $f12, tmp_float14           # write float14
    li $v0, 2
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    la $t0, txt_1                   # string15 = "Hola MIPS"
    sw $t0, tmp_string15
    lw $t0, tmp_string15            # saludo = string15
    sw $t0, var_saludo
    lw $t0, var_saludo              # string16 = saludo
    sw $t0, tmp_string16
    lw $a0, tmp_string16            # write string16
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    la $t0, txt_2                   # string17 = "fin de textos"
    sw $t0, tmp_string17
    lw $a0, tmp_string17            # write string17
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall
    l.s $f0, var_x                  # float18 = x
    s.s $f0, tmp_float18
    l.s $f0, var_y                  # float19 = y
    s.s $f0, tmp_float19
    l.s $f0, tmp_float18            # bool20 = float18 > float19
    l.s $f2, tmp_float19
    c.lt.s $f2, $f0
    li $t2, 1
    bc1t cmp_listo_1
    li $t2, 0

cmp_listo_1:
    sw $t2, tmp_bool20
    lw $t0, tmp_bool20              # if bool20 goto principal$if$1$bloque1
    bnez $t0, principal.if.1.bloque1
    j principal.if.1.siguiente1     # goto principal$if$1$siguiente1

principal.if.1.bloque1:
    la $t0, txt_3                   # string21 = "x es mayor"
    sw $t0, tmp_string21
    lw $a0, tmp_string21            # write string21
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.1.siguiente1:

principal.if.1.final:
    li $t0, 65                      # char22 = 'A'
    sw $t0, tmp_char22
    lw $t0, tmp_char22              # letra = char22
    sw $t0, var_letra
    lw $t0, var_letra               # char23 = letra
    sw $t0, tmp_char23
    li $t0, 65                      # char24 = 'A'
    sw $t0, tmp_char24
    lw $t0, tmp_char23              # bool25 = char23 == char24
    lw $t1, tmp_char24
    seq $t2, $t0, $t1
    sw $t2, tmp_bool25
    lw $t0, tmp_bool25              # if bool25 goto principal$if$2$bloque1
    bnez $t0, principal.if.2.bloque1
    j principal.if.2.siguiente1     # goto principal$if$2$siguiente1

principal.if.2.bloque1:
    la $t0, txt_4                   # string26 = "la letra es A"
    sw $t0, tmp_string26
    lw $a0, tmp_string26            # write string26
    li $v0, 4
    syscall
    li $a0, 10
    li $v0, 11
    syscall

principal.if.2.siguiente1:

principal.if.2.final:
    li $v0, 10                      # void$principal$final
    syscall
