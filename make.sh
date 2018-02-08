#!/usr/bin/env bash
set -euC

CLASS_NAME='MatchThree'     # Main class name.
EXIT_BAD_VERB=1             # Exit code - Unrecognized verb.
EXIT_MISSING_VERB=2         # Exit code - No verbs provided.
EXIT_OK=0                   # Exit code - Success
EXIT_UNIMPLEMENTED=-1       # Exit code - Feature not implemented.
MAIN_FILE='MatchThree.java' # Main class file name.
RESOURCE='resources'        # Resource directory.
SOURCE='src'                # Source code directory.
TARGET='target'             # Target directory.
TARGET_DOC='doc'            # Documentation artifact directory name.
TARGET_MAIN='main'          # Main artifact directory name.

script_dir='.' # Path to script. Updated at run-time.

build () {
	cd -- "${script_dir}/${SOURCE}"
	mkdir -p -- "${script_dir}/${TARGET}/${TARGET_MAIN}"
	javac \
		-encoding UTF-8 \
		-Xlint:all \
		-d "${script_dir}/${TARGET}/${TARGET_MAIN}" \
		"${script_dir}/${SOURCE}/${MAIN_FILE}"
	ln -snf \
		"../../${RESOURCE}" \
		"${script_dir}/${TARGET}/${TARGET_MAIN}/${RESOURCE}"
}

clean () {
	printf 'clean: Not yet implemented\n' 1>&2
	exit "$EXIT_UNIMPLEMENTED"
}

doc () {
	mkdir -p -- "${script_dir}/${TARGET}/${TARGET_DOC}"
	# TODO: Avoid wildstar character, use find(1) instead?
	javadoc \
		-locale en_US \
		-private \
		-encoding UTF-8 \
		-keywords \
		-docencoding UTF-8 \
		-d "${script_dir}/${TARGET}/${TARGET_DOC}" \
		"${script_dir}/${SOURCE}/"*".java"
}

get_path () {
	script_dir="$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" && pwd )"
}

print_usage () {
	printf 'usage: make.sh [build|run]\n'
}

run () {
	cd -- "${script_dir}/${TARGET}/${TARGET_MAIN}"
	java "$CLASS_NAME"
}

main () {
	# Get script directory #
	get_path
	
	# Print usage #
	if [ "$#" -lt 1 ]
	then
		print_usage 1>&2
		exit "$EXIT_MISSING_VERB"
	fi
	
	# Execute verbs #
	for verb in "$@"
	do
		case "$verb" in
			build) build ;;
			clean) clean ;;
			doc)   doc ;;
			help)  print_usage ;;
			run)   run ;;
			*)
				printf 'Verb not recognized: "%s"\n' "$1" 1>&2
				print_usage 1>&2
				exit "$EXIT_BAD_VERB"
		esac
	done
	
	# Success #
	exit "$EXIT_OK"
}

main "$@"
