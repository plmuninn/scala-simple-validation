import pl.muninn.markdown.Markdown.{*, given}

def shortAbout(using MarkdownConfig) =
    md {
        h1("About")
        p{
            code("scala-simple-validation") + m" was designed to allow in simple way validate case classes and other"
            m" data structures. It provides:"
            br
            ul{
                li(m"easy way for describing validation schema")
                li(m"few common validators to use")
                li(m"simple way of creating your own, custom validators")
                li(m"designing more complex validation process - where validation depends on some specific value")
                li(m"allow to reuse validation schemas as validators")
            }
        }
    }


def markdown(using MarkdownConfig) = md {
    add(shortAbout)
    p{
     m"Library was designed in a way to be easy to use and quite elastic. It was created because using"
     m" cats " + a("Validated", "https://typelevel.org/cats/datatypes/validated.html") + m" was really"
     m" repetitive and other libraries are too \"complicated\" in my opinion."
     m" I wanted to create something simple and easy to understand."
    }
    br
    h1("Other validation libraries")
    p{
     m"They are other validation libraries that should mention here - some of them were inspiration for this one:"
     br
     ol {
       li(a("accord", "http://wix.github.io/accord/"))
       li(a("dupin", "https://github.com/yakivy/dupin"))
       li(a("octopus", "https://github.com/krzemin/octopus"))
       li(a("fields", "https://jap-company.github.io/fields/"))
     }
    }
    br
    h1("Dependencies")
    p{
      m"Libraray is build using " + a("cats", "https://typelevel.org/cats/") + m" in version " + b(m"@CATS_VERSION@")
    }
}