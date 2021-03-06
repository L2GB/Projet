//
//  ZByteOperations.h
//  Part of Z-Way.C library
//
//  Created by Alex Skalozub on 2/4/12.
//  Based on Z-Way source code written by Christian Paetz and Poltorak Serguei
//
//  Copyright (c) 2012 Z-Wave.Me
//  All rights reserved
//  info@z-wave.me
//
//  This source file is subject to the terms and conditions of the
//  Z-Wave.Me Software License Agreement which restricts the manner
//  in which it may be used.
//

#ifndef zway_byte_operations_h
#define zway_byte_operations_h

#define MAX(x, y) (((x) > (y)) ? (x) : (y))
#define MIN(x, y) (((x) < (y)) ? (x) : (y))

ZWEXPORT_PRIVATE int bytes_to_int(const ZWBYTE *bytes, ZWBYTE length);
ZWEXPORT_PRIVATE int bytes_to_int_le(const ZWBYTE *bytes, ZWBYTE length);

ZWEXPORT_PRIVATE void int_to_bytes(int value, ZWBYTE *bytes, ZWBYTE length);
ZWEXPORT_PRIVATE void int_to_bytes_le(int value, ZWBYTE *bytes, ZWBYTE length);

ZWEXPORT_PRIVATE char *bytes_to_string(const ZWBYTE *bytes, ZWBYTE length);
ZWEXPORT_PRIVATE void string_to_bytes(const char *string, ZWBYTE **bytes, size_t *size);

ZWEXPORT_PRIVATE void clear_bitmask(ZWBYTE *bitmask);

ZWEXPORT_PRIVATE ZWBYTE float_to_bytes(float value, ZWBYTE *bytes, ZWBYTE *detectedPrecision);

ZWEXPORT_PRIVATE uint16_t crc16(const ZWBYTE *bytes, size_t length);

#endif
