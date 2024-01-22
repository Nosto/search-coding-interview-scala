export function escapeHtml(unsafe) {
    return (unsafe ?? '').toString()
         .replace(/&/g, "&amp;")
         .replace(/</g, "&lt;")
         .replace(/>/g, "&gt;")
         .replace(/"/g, "&quot;")
         .replace(/'/g, "&#039;");
}

export function extractWords(value) {
    return value
        .split(/[ -./\\()"',;<>~!@#$%^&*|+=[\]{}`~?:]+/)
        .filter((v) => v !== '')
}

export function indexBy(
    array,
    keyCallback
) {
    return array.reduce((prev, cur) => {
        const finalKey = keyCallback(cur)
        ;(finalKey instanceof Array ? finalKey : [finalKey]).forEach(key => {
            if (key in prev) {
                prev[key].add(cur)
            } else {
                prev[key] = new Set([cur])
            }
        });

        return prev
    }, {})
}


export function intersection(setA, ...otherSets) {
    const intersection = new Set()
    for (const elem of (setA || new Set())) {
        if (otherSets.every(v => v.has(elem))) {
            intersection.add(elem)
        }
    }
    return intersection
}

export function removeKey(obj, key) {
    const {[key]: _, ...rest} = obj
    return rest
}

export function mapSet(set, cb) {
    const newSet = new Set()
    for (const value of set) {
        newSet.add(cb(value))
    }
    return newSet
}

export function union(...otherSets) {
    const union = new Set()

    for (const otherSet of otherSets) {
        for (const value of otherSet) {
            union.add(value)
        }
    }

    return union
}
