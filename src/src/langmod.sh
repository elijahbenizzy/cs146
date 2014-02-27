#!/bin/bash

# project 1
# language modelling

training=$1
heldout=$2
testdata=$3
goodbad=$4


javac */*.java 
java langmod.Main $training $heldout $testdata $goodbad bigram
# 
# Make TWO copies of this script, one to test your unigram model and one
# for your bigram model. Save them under the filenames 'unigram' and 'bigram'
# 
# Fill in the empty space below with whatever sequence of commands will run
# your code and print the following values to standard output in this order,
# one on each line:
#
# (1) The log probability of '$testdata' according to a model trained on
#       '$training', with smoothing parameters set to 1. (Remember to include
#       the dollar sign when passing the arguments to your code!)
# (2) The log probability of '$testdata' according to a model trained on
#       '$training', with smoothing parameters optimized according to the
#       heldout data '$heldout'.
# (3) The percentage of sentence pairs in '$goodbad' identified correctly,
#       according to the model with optimized parameter(s).
# (4) The optimized value of alpha.
# (5) The optimized value of beta (bigram model only).
##############################################################################

##############################################################################
# Run this script with the command (all on one line)
# 
#       ./langmod english-senate-0.txt english-senate-1.txt 
#               english-senate-2.txt good-bad.txt
