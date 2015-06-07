(ns omnichromacy.workspace
  (:require [omnichromacy.io :as io]
            [omnichromacy.data-shapes :as dshapes]
            [omnichromacy.stats :as stats]
            [omnichromacy.viz :as viz]
            [clojure.core.matrix :as mat]
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
  (def beltsville-bil
    (time
      (io/slurp-image-cube "data/0810_2022_ref.dat" [600 360 320] :short)))

  (def beltsville-spectral-slice
    (time
      (-> beltsville-bil (mat/slice 1)
                         (mat/slice 1 1))))

  (let [beltsville-plot (viz/spectral-plot (into [] beltsville-spectral-slice))]
    (view beltsville-plot)
    (save beltsville-plot "spectralplot.png"))

)


