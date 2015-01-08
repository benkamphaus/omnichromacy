(ns omnichromacy.workspace
  (:require [omnichromacy.io :as io]
            [omnichromacy.data-shapes :as dshapes]
            [omnichromacy.stats :as stats]
            [omnichromacy.viz :as viz]
            [incanter.core :refer [view save]]))

; A list of wip functions that utilize these proto-modules

(comment

  ; repl starting point
  (require 'omnichromacy.workspace)
  (in-ns 'omnichromacy.workspace)
  (use 'clojure.repl)

  (def nireland-bsq
    (time
      (io/slurp-image-cube "data/nireland.dat" [472 682 128] :short)))

  (def nireland-bip
    (time
      (dshapes/change-interleave nireland-bsq :bsq :bip)))

  (def nireland-mat
    (time
      (dshapes/as-spectral-matrix nireland-bip)))

  (def nireland-stats
    (time
      (stats/band-stats nireland-mat)))

  ; Can get stats like this:
  (:mean nireland-stats)

  (view (viz/scatter nireland-mat 20 21))

  (view (viz/spectral-plot nireland-mat 10000))

  ; beltsville bil image workflow
  (def beltsville 
    (time
      (io/slurp-image-cube "data/0810_2022_ref.dat" [320 360 600] :short)))

  (def beltsville-bip
    (time
      (dshapes/change-interleave beltsville :bil :bip)))

  (def beltsville-mat
    (time
      (dshapes/as-spectral-matrix beltsville-bip)))

  (view (viz/spectral-plot beltsville-mat 1200))
  (save (viz/spectral-plot beltsville-mat 1200) "spectralplot.png")

)

