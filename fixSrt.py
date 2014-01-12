#!/usr/bin/env python

import sys
import codecs
from datetime import datetime, timedelta

ENCODING = "ISO-8859-1"
SEPARATOR_DATES = " --> "
EXTENSION_SRT = ".srt"
FIXED_SUFFIX = "-fixed"

def usage(app):
  print "usage: "
  print " " + app + " file.srt delay-seconds"
  print " note: delay can be positive or negative"
  print "   positive: forward subtitles"
  print "   negative: reward subtitles"
  sys.exit(-1)

def checkArgs(args):
  if len(args) != 3:
    usage(args[0])

def fixDate(line, delay):
  fechas = line.split(SEPARATOR_DATES)
  return desplazaFecha(fechas[0], delay)+ SEPARATOR_DATES+desplazaFecha(fechas[1][:-2], delay) 

def desplazaFecha(fecha, delay):
  fechaDesplazada = datetime.strptime(fecha, "%H:%M:%S,%f").replace(year=2013)
  fechaDesplazada = fechaDesplazada + timedelta(seconds=delay) 
  fechaFormateada = fechaDesplazada.strftime("%H:%M:%S,%f")[:-3]
  return str(fechaFormateada)

def main(argv):
  checkArgs(argv)
  inputfile = argv[1]
  try: delay = int(argv[2])
  except ValueError: 
    print "ERROR: delay must be a number"
    usage(argv[0]) 

  filename_fixed = inputfile.replace(EXTENSION_SRT, FIXED_SUFFIX+EXTENSION_SRT)
  
  try: 
    with codecs.open(inputfile, "r", ENCODING) as toFix, codecs.open(filename_fixed, "w", ENCODING) as fixed:
      for line in toFix:
        if " --> " in line: 
          fixed.write(fixDate(line, delay) + "\n")
        else: 
          fixed.write(line[:-2] + "\n")
  except IOError: 
    print "ERROR: '" + inputfile+ "' must be exists"
    usage(argv[0])
  toFix.close()
  fixed.close()

if __name__ == "__main__":
   main(sys.argv)

