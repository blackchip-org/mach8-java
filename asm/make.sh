#!/bin/bash

set -e

ASM="/usr/local/bin/ophis -65c02"
DIR=$(dirname $0)
SRC=${DIR}/asm
DEST=${DIR}/../build/classes/asm

cd ${DIR}

${ASM} ${SRC}/system/start.asm ${DEST}/system/start.mob

${ASM} ${SRC}/example/hello.asm ${DEST}/example/hello.mob
${ASM} ${SRC}/example/color.asm ${DEST}/example/color.mob
${ASM} ${SRC}/example/border.asm ${DEST}/example/border.mob
${ASM} ${SRC}/example/text.asm ${DEST}/example/text.mob

${ASM} ${SRC}/test/test_memcpy.asm ${DEST}/test/test_memcpy.mob
${ASM} ${SRC}/test/test_scroll.asm ${DEST}/test/test_scroll.mob

if ! ./pack.pl asm/kernel ; then
    exit 1
fi


