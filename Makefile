default: hello

hello:
	@echo "Hello World"

pdf:
	asciidoctor-pdf -a pdf-style=./doc/assets/book.yml -a pdf-fontsdir=./doc/assets/fonts doc/index.adoc