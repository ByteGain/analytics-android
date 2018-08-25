# Replace all non-filename references to a word (not containing a .filetype) in all files of
# specified type in a file tree with a new expression

from os.path import dirname, join, abspath, splitext
import os
import os.path


def replace(dirpath, old_word, new_word, filetypes, changelog_path):
    old_word = "com." + old_word
    new_word = "com." + new_word
    with open(changelog_path, "a") as changelog:
        dirdata = os.walk(dirpath)

        for (top_dir, subdirs, file_names) in dirdata.__iter__():
            for file_name in file_names:
                if splitext(file_name)[1] in filetypes:
                    filepath = join(top_dir, file_name)
                    changelog.write(filepath + "\n")

                    with open(filepath, "r") as f:
                        lines = []
                        for line in f:
                            if old_word in line:
                                updated = line.replace(old_word, new_word)
                                lines.append(updated)
                                changelog.write("\tReplaced: " + line)
                                changelog.write("\tWith: " + updated)
                            else:
                                lines.append(line)
                    with open(join(top_dir, file_name), "w") as f:
                        f.writelines(lines)

def rename(dirpath, old_name, new_name, changelog_path):
    with open(changelog_path, "a") as changelog:
        dirdata = os.walk(dirpath)

        for (top_dir, subdirs, file_names) in dirdata.__iter__():
            if os.path.basename(top_dir) != "com":
                continue
            for i in xrange(len(subdirs)):
                subdir = subdirs[i]
                if subdir == old_name:
                    subdirs[i] = new_name
                    os.rename(os.path.join(top_dir, old_name), os.path.join(top_dir, new_name))
                    changelog.write("Renamed %s/{%s,%s}\n" % (top_dir, old_name, new_name))


# do
replace(".", "segment", "bytegain", [".java"], abspath("./changelog.txt"))
rename(".", "segment", "bytegain", abspath("./changelog.txt"))
# Undo
#replace(".", "bytegain", "segment", [".java"], abspath("./changelog.txt"))
#rename(".", "bytegain", "segment", abspath("./changelog.txt"))
