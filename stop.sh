#!/usr/bin/env bash
ps -ef|grep 'nbi-1.0'|grep -v grep|cut -c 9-15|xargs --no-run-if-empty kill -9