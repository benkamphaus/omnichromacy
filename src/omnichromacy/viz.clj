(ns omnichromacy.viz
  (:require [incanter.charts :refer [scatter-plot line-chart]]
            [clojure.core.matrix :refer [slice transpose]]))

(defn scatter
  "Scatter plot of a spectral matrix, must pass index for band1 and band2 as
  arguments"
  [spectral-mat b1-ind b2-ind]
  (let [b1-vals (into [] (slice spectral-mat b1-ind))
        b2-vals (into [] (slice spectral-mat b2-ind))]
    (scatter-plot b1-vals b2-vals)))

(defn spectral-plot
  "Plots spectrum at given pixel when passed spectral mat and collapsed coord,
  or vector of values as a spectrum"
  ([spectrum]
    (line-chart (range (count spectrum)) spectrum
                :x-label "Wavelength" :y-label "Reflectance"))
  ([spectral-mat coord]
    (spectral-plot (into [] (slice (transpose spectral-mat) coord)))))
