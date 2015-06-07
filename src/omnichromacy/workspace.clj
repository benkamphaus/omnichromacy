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

  (def nireland-img
    (time
      (io/slurp-image-cube "data/nireland.dat" [128 682 472] :short)))

  (def nireland-mat
    (time
      (dshapes/as-spectral-matrix nireland-img)))

  (def nireland-stats
    (time
      (stats/band-stats nireland-mat)))

  ; Can get stats like this:
  (:mean nireland-stats)

  (view (viz/scatter nireland-mat 70 110))

  (view (viz/spectral-plot nireland-mat 10000))
  (view (viz/spectral-plot (into [] (:mean nireland-stats))))

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


