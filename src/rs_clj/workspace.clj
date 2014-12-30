(ns rs-clj.workspace
  (:require [rs-clj.io :as io]
            [rs-clj.data-shapes :as dshapes])
  (:use [clojure.core.matrix]))

; A list of wip functions that utilize these proto-modules

(comment

  ; repl starting point
  (require 'rs-clj.workspace)
  (in-ns 'rs-clj.workspace)
  (use 'clojure.repl)

  (def nireland-bsq
    (time
      (io/slurp-image-cube "data/nireland.dat" [472 682 128] :short)))

  (def nireland-bip
    (time
      (dshapes/change-interleave nireland-bsq :bsq :bil)))

)


