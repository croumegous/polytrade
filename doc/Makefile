default: setup

setup:
	apt install -y ruby-full npm
	gem install bundler asciidoctor-pdf coderay asciidoctor-diagram
	npm i
	bundle
	bundle install

build: build-pdf

build-pdf:
	asciidoctor-pdf -a pdf-style=./assets/book.yml -a pdf-fontsdir=./assets/fonts index.adoc
