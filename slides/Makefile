
MARP=marp
OPT=--theme my-theme.css --allow-local-files
OUTPUTDIR=output/
TARGETS=room flutter
TARGETS_PDF=$(addprefix $(OUTPUTDIR), $(addsuffix .pdf, $(TARGETS)))
TARGETS_PPTX=$(addprefix $(OUTPUTDIR), $(addsuffix .pptx, $(TARGETS)))
TARGETS_HTML=$(addprefix $(OUTPUTDIR), $(addsuffix .html, $(TARGETS)))

all: pdf html pptx

pdf: $(TARGETS_PDF)
pptx: $(TARGETS_PPTX)
html: $(TARGETS_HTML)

.PHONY : clean
clean:
	rm -f output/*

$(OUTPUTDIR)%.pdf : %.md my-theme.css
	$(MARP) $(OPT) $< -o $@

$(OUTPUTDIR)%.pptx : %.md my-theme.css
	$(MARP) $(OPT) $< -o $@

$(OUTPUTDIR)%.html : %.md my-theme.css
	$(MARP) $(OPT) $< -o $@